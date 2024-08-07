package fete.bird.feature.assessment;

import fete.bird.feature.course.Course;
import io.micronaut.core.type.Argument;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller("bff/assessment")
public class AssessmentController {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public AssessmentController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Get
    public List<AssessmentResponse> get() throws ExecutionException, InterruptedException, IOException {
        List<AssessmentResponse> assessmentResponses;
        List<URI> uris = List.of(
                URI.create("http://localhost:8082/assessment"),
                URI.create("http://localhost:8081/course")
        );
        List<HttpRequest> requests = uris.stream()
                .map(uri -> HttpRequest.newBuilder().uri(uri).build())
                .toList();

        List<CompletableFuture<HttpResponse<String>>> futures = requests.stream()
                .map(httpRequest -> client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        List<Assessment> assessments = new ArrayList<>();
        final List<Course> courses = new ArrayList<>();
        for (CompletableFuture<HttpResponse<String>> future : futures) {
            HttpResponse<String> response = future.get();
            String responseBody = response.body();

            if (response.uri().getPath().endsWith("assessment")) {
                assessments = objectMapper.readValue(responseBody, Argument.listOf(Assessment.class));
            } else if (response.uri().getPath().endsWith("course")) {
                courses.addAll(objectMapper.readValue(responseBody, Argument.listOf(Course.class)));
            }
        }
        assessmentResponses = assessments.stream()
                .map(assessment -> {
                    Course course = courses.stream().filter(c -> c.id().equals(assessment.courseId())).findFirst().orElse(null);
                    return new AssessmentResponse(assessment.id(),
                            assessment.name(),
                            assessment.description(),
                            course,
                            assessment.createdDate(),
                            assessment.dueDate());
                }).toList();
        return assessmentResponses;
    }
}
