package fete.bird;

import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.assessment.AssessmentResponse;
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
public class AssessmentTest extends BaseFixture{
    private final String assessmentEndpoint = "/assessment";

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s", assessmentEndpoint, UUID.randomUUID()));
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
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentResponse createbody = response.body();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, AssessmentResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the assessment by id")
    void shouldGetTheAssessmentById() {
        // Give
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentResponse createbody = response.body();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,AssessmentResponse.class);
        AssessmentResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }

    @Test
    @DisplayName("Should get all assessment")
    void shouldGetAllAssessment() {
        // Given
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET(assessmentEndpoint);
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the Assessment")
    void shouldCreateTheAssessment() {
        // Give
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        // When
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentEndpoint, UUID.randomUUID()), requestModel);
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
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        httpClient.toBlocking().exchange(request, AssessmentResponse.class);
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
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var createRequest = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentEndpoint, response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, AssessmentResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the course and fetch the updated value")
    void shouldUpdateTheCourseAndFetchTheUpdatedValue() {
        // Give
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var createRequest = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, AssessmentResponse.class);
        var lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", assessmentEndpoint, lastCreatedCountryId), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, AssessmentResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s", assessmentEndpoint, lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,AssessmentResponse.class);
        AssessmentResponse getbody = getResponse.body();
        assertEquals(getbody.name(), patchResponse.body().name());
    }

    @Test
    @DisplayName("Should delete the assessment")
    void shouldDeleteTheAssessment() {
        // Give
        AssessmentRequest requestModel = testDataGenerator.generateRecord(AssessmentRequest.class);
        var request = HttpRequest.POST(assessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s", assessmentEndpoint, body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the course")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheCourse() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", assessmentEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}