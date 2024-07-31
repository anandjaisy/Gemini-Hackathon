package fete.bird.feature.assessment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fete.bird.feature.course.Course;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller("bff/assessment")
public class AssessmentController {
    @Get
    public Optional<List<AssessmentResponse>> get() throws ExecutionException, InterruptedException, IOException {
        List<AssessmentResponse> responseToReturn = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try (HttpClient client = HttpClient.newHttpClient()) {
            List<URI> uris = List.of(
                    URI.create("http://localhost:8080/assessment"),
                    URI.create("http://localhost:8081/course")
            );
            List<HttpRequest> requests = uris.stream()
                    .map(uri -> HttpRequest.newBuilder().uri(uri).build())
                    .toList();
            List<CompletableFuture<HttpResponse<String>>> futures = requests.stream()
                    .map(httpRequest -> client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()))
                    .toList();
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            for (CompletableFuture<HttpResponse<String>> future : futures) {
                HttpResponse<String> response = future.get();
                if (response.uri().getPath().endsWith("assessment")) {
                    List<Assessment> assessments = objectMapper.readValue(response.body(), new TypeReference<>() {});
                } else if (response.uri().getPath().endsWith("course")) {
                    List<Course> course = objectMapper.readValue(response.body(), new TypeReference<>() {});
                }
            }
        }
        return Optional.empty();
    }
}