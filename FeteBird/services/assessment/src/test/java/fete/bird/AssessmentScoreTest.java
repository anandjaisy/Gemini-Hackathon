package fete.bird;

import fete.bird.feature.assessmentScore.AssessmentScoreRequest;
import fete.bird.feature.assessmentScore.AssessmentScoreResponse;
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
public class AssessmentScoreTest extends BaseFixture{
    private final String assessmentScoreEndpoint = "/assessment/score";

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s", assessmentScoreEndpoint, UUID.randomUUID()));
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
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentScoreResponse createbody = response.body();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentScoreEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, AssessmentScoreResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the assessment score by id")
    void shouldGetTheAssessmentScoreById() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentScoreResponse createbody = response.body();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentScoreEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,AssessmentScoreResponse.class);
        AssessmentScoreResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }


    @Test
    @DisplayName("Should get all assessment score")
    void shouldGetAllAssessmentScore() {
        // Given
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET(assessmentScoreEndpoint);
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the Assessment score")
    void shouldCreateTheAssessmentScore() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        // When
        var response = httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentScoreEndpoint, UUID.randomUUID()), requestModel);
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(patchRequest);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw an un accepted entity on duplicate assessment name")
    void shouldThrowAnUnAcceptedEntityOnDuplicateAssessmentName() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
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
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var createRequest = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, AssessmentScoreResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentScoreRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentScoreEndpoint, response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, AssessmentScoreResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the assessment score and fetch the updated value")
    void shouldUpdateTheAssessmentScoreAndFetchTheUpdatedValue() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var createRequest = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, AssessmentScoreResponse.class);
        var lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentScoreRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentScoreEndpoint, lastCreatedCountryId), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, AssessmentScoreResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentScoreEndpoint, lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,AssessmentScoreResponse.class);
        AssessmentScoreResponse getbody = getResponse.body();
        assertEquals(getbody.score(), patchResponse.body().score());
    }

    @Test
    @DisplayName("Should delete the assessment score")
    void shouldDeleteTheAssessmentScore() {
        // Give
        AssessmentScoreRequest requestModel = testDataGenerator.generateRecord(AssessmentScoreRequest.class);
        var request = HttpRequest.POST(assessmentScoreEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentScoreResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentScoreResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s", assessmentScoreEndpoint, body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the assessment score")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheAssessmentScore() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", assessmentScoreEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
