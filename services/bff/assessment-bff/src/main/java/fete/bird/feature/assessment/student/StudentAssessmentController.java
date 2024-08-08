package fete.bird.feature.assessment.student;

import fete.bird.feature.assessment.question.Question;
import fete.bird.feature.enrolment.Enrolment;
import fete.bird.feature.user.User;
import io.micronaut.core.type.Argument;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
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

@Controller("bff/assessment/student")
@ExecuteOn(TaskExecutors.BLOCKING)
public class StudentAssessmentController {
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public StudentAssessmentController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = HttpClient.newHttpClient();
    }

    @Get
    public List<StudentAssessmentResponse> find(@Header("Authorization") String authorizationHeader,
                                                @QueryValue String assessmentId,
                                                @QueryValue String questionId)
            throws InterruptedException, IOException, ExecutionException {
        List<StudentAssessmentResponse> enrolmentResponses;
        List<URI> uris = List.of(
                URI.create("http://localhost:5001/admin/realms/FeteBird/users"),
                URI.create(String.format("http://localhost:8081/question?assessmentId=%s&questionId=%s", assessmentId, questionId))
        );

        List<Callable<HttpResponse<String>>> callables = uris.stream().map(uri -> (Callable<HttpResponse<String>>) () -> {
            HttpRequest request = HttpRequest.newBuilder().uri(uri)
                    .header("Authorization", authorizationHeader)
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }).toList();

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<HttpResponse<String>>> futures = executorService.invokeAll(callables);

            List<Question> questionList = new ArrayList<>();
            List<User> users = new ArrayList<>();
            List<Enrolment> enrolments = new ArrayList<>();

            for (Future<HttpResponse<String>> future : futures) {
                HttpResponse<String> response = future.get();
                String body = response.body();
                if (response.uri().getPath().endsWith("users")) {
                    users.addAll(objectMapper.readValue(body, Argument.listOf(User.class)));
                } else if (response.uri().getPath().endsWith("question")) {
                    questionList.addAll(objectMapper.readValue(body, Argument.listOf(Question.class)));
                }
            }
            enrolmentResponses = enrolments.stream().map(enrolment -> {
                Question course = questionList.stream().filter(x -> x.id().equals(enrolment.courseId())).findFirst().orElse(null);
                User professor = users.stream().filter(x -> x.id().equals(enrolment.professorId())).findFirst().orElse(null);
                User student = users.stream().filter(x -> x.id().equals(enrolment.studentId())).findFirst().orElse(null);
                return new StudentAssessmentResponse();
            }).toList();
        }
        return enrolmentResponses;
    }
}
