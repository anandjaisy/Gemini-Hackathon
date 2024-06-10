package fete.bird;

import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.question.QuestionRequest;
import fete.bird.feature.question.QuestionResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class QuestionTest extends BaseFixture{

    private final String questionEndpoint = "/question";

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s", questionEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw the status 200 in valid Id")
    void shouldThrowTheStatus200InValidId() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, QuestionResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        QuestionResponse createbody = response.body();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s", questionEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, QuestionResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the question by id")
    void shouldGetTheQuestionById() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, QuestionResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        QuestionResponse createbody = response.body();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s", questionEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,QuestionResponse.class);
        QuestionResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }

    @Test
    @DisplayName("Should get all questions")
    void shouldGetAllQuestions() {
        // Given
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, QuestionResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET(questionEndpoint);
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the question")
    void shouldCreateTheQuestion() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        // When
        var response = httpClient.toBlocking().exchange(request, QuestionResponse.class);
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", questionEndpoint, UUID.randomUUID()), requestModel);
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(patchRequest);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw an un accepted entity on duplicate question name")
    void shouldThrowAnUnAcceptedEntityOnDuplicateAssessmentName() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        httpClient.toBlocking().exchange(request, QuestionResponse.class);
        // When
        HttpClientResponseException exception = assertThrows(
                HttpClientResponseException.class,
                () -> httpClient.toBlocking().exchange(request)
        );
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Should update the Assessment")
    void shouldUpdateTheAssessment() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var createRequest = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, QuestionResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", questionEndpoint, response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, QuestionResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the course and fetch the updated value")
    void shouldUpdateTheCourseAndFetchTheUpdatedValue() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var createRequest = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, QuestionResponse.class);
        var lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        QuestionRequest courseToUpdateRequest = testDataGenerator.generateRecord(QuestionRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", questionEndpoint, lastCreatedCountryId), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, QuestionResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s", questionEndpoint, lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,QuestionResponse.class);
        QuestionResponse getbody = getResponse.body();
        assertEquals(getbody.question(), patchResponse.body().question());
    }

    @Test
    @DisplayName("Should delete the question")
    void shouldDeleteTheQuestion() {
        // Give
        QuestionRequest requestModel = testDataGenerator.generateRecord(QuestionRequest.class);
        var request = HttpRequest.POST(questionEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, QuestionResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        QuestionResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s", questionEndpoint, body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the course")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheCourse() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", questionEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
