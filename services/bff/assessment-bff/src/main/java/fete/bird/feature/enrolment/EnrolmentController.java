package fete.bird.feature.enrolment;


import fete.bird.feature.course.Course;
import fete.bird.feature.user.User;
import io.micronaut.core.type.Argument;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.serde.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Controller("bff/enrolment")
@ExecuteOn(TaskExecutors.BLOCKING)
public class EnrolmentController {
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public EnrolmentController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = HttpClient.newHttpClient();
    }

    @Get
    public List<EnrolmentResponse> find(@Header("Authorization") String authorizationHeader) throws InterruptedException, IOException, ExecutionException {
        List<EnrolmentResponse> enrolmentResponses = new ArrayList<>();
        List<URI> uris = List.of(
                URI.create("http://localhost:5001/admin/realms/FeteBird/users"),
                URI.create("http://localhost:8081/course"),
                URI.create("http://localhost:8081/enrolment")
        );

        List<Callable<HttpResponse<String>>> callables = uris.stream().map(uri -> (Callable<HttpResponse<String>>) () -> {
            HttpRequest request = HttpRequest.newBuilder().uri(uri)
                    .header("Authorization", authorizationHeader)
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }).toList();

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<HttpResponse<String>>> futures = executorService.invokeAll(callables);

            List<Course> courses = new ArrayList<>();
            List<User> users = new ArrayList<>();
            List<Enrolment> enrolments = new ArrayList<>();

            for (Future<HttpResponse<String>> future : futures) {
                HttpResponse<String> response = future.get();
                String body = response.body();
                if (response.uri().getPath().endsWith("users")) {
                    users.addAll(objectMapper.readValue(body, Argument.listOf(User.class)));
                } else if (response.uri().getPath().endsWith("course")) {
                    courses.addAll(objectMapper.readValue(body, Argument.listOf(Course.class)));
                }else if (response.uri().getPath().endsWith("enrolment")) {
                    enrolments.addAll(objectMapper.readValue(body, Argument.listOf(Enrolment.class)));
                }
            }
            enrolmentResponses = enrolments.stream().map(enrolment -> {
                Course course = courses.stream().filter(x->x.id().equals(enrolment.courseId())).findFirst().orElse(null);
                User professor = users.stream().filter(x -> x.id().equals(enrolment.professorId())).findFirst().orElse(null);
                User student = users.stream().filter(x-> x.id().equals(enrolment.studentId())).findFirst().orElse(null);
                return new EnrolmentResponse(enrolment.id(), course, professor,student);
            }).toList();
        }
        return enrolmentResponses;
    }
}
