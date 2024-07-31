package fete.bird;

import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.assessment.AssessmentResponse;
import fete.bird.feature.studentAssessment.StudentAssessmentRequest;
import fete.bird.feature.studentAssessment.StudentAssessmentResponse;
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
public class StudentAssessmentTest extends BaseFixture{
    private final String studentAssessmentEndpoint = "/assessment/student";

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s", studentAssessmentEndpoint, UUID.randomUUID()));
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
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, StudentAssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        StudentAssessmentResponse createbody = response.body();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s", studentAssessmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, StudentAssessmentResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the student assessment by id")
    void shouldGetTheStudentAssessmentById() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, StudentAssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        StudentAssessmentResponse createbody = response.body();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s", studentAssessmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,StudentAssessmentResponse.class);
        StudentAssessmentResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }

    @Test
    @DisplayName("Should get all assessment")
    void shouldGetAllAssessment() {
        // Given
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, StudentAssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET(studentAssessmentEndpoint);
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the Assessment")
    void shouldCreateTheAssessment() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        // When
        var response = httpClient.toBlocking().exchange(request, StudentAssessmentResponse.class);
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", studentAssessmentEndpoint, UUID.randomUUID()), requestModel);
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
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        httpClient.toBlocking().exchange(request, StudentAssessmentResponse.class);
        // When
        HttpClientResponseException exception = assertThrows(
                HttpClientResponseException.class,
                () -> httpClient.toBlocking().exchange(request)
        );
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Should update the student assessment")
    void shouldUpdateTheStudentAssessment() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var createRequest = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, StudentAssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        AssessmentRequest courseToUpdateRequest = testDataGenerator.generateRecord(AssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", studentAssessmentEndpoint, response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, StudentAssessmentResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the student assessment and fetch the updated value")
    void shouldUpdateTheStudentAssessmentAndFetchTheUpdatedValue() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var createRequest = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(createRequest, StudentAssessmentResponse.class);
        var lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        StudentAssessmentRequest courseToUpdateRequest = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", studentAssessmentEndpoint, lastCreatedCountryId), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, StudentAssessmentResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s", studentAssessmentEndpoint, lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,StudentAssessmentResponse.class);
        StudentAssessmentResponse getbody = getResponse.body();
        assertEquals(getbody.answer(), patchResponse.body().answer());
    }

    @Test
    @DisplayName("Should delete the assessment")
    void shouldDeleteTheAssessment() {
        // Give
        StudentAssessmentRequest requestModel = testDataGenerator.generateRecord(StudentAssessmentRequest.class);
        var request = HttpRequest.POST(studentAssessmentEndpoint, requestModel);
        var response = httpClient.toBlocking().exchange(request, AssessmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        AssessmentResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s", studentAssessmentEndpoint, body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the course")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheCourse() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", studentAssessmentEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
