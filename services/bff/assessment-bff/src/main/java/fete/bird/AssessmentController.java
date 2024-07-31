package fete.bird;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

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
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    @Get
    public List<AssessmentResponse> get() throws ExecutionException, InterruptedException, JsonProcessingException {
        List<AssessmentResponse> assessmentResponses = new ArrayList<>();
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
                assessments = objectMapper.readValue(responseBody, new TypeReference<>() {});
            } else if (response.uri().getPath().endsWith("course")) {
                courses.addAll(objectMapper.readValue(responseBody, new TypeReference<>() {}));
            }
        }
        assessmentResponses = assessments.stream()
                .map(assessment -> {
                    Course course = courses.stream().filter(c -> c.id().equals(assessment.courseId())).findFirst().orElse(null);
                    return new AssessmentResponse(assessment.id(),
                            assessment.name(),
                            assessment.description(),
                            course,
                            assessment.assessmentDate(),
                            assessment.assessmentDueDate());
                }).toList();
        return assessmentResponses;
    }
}
