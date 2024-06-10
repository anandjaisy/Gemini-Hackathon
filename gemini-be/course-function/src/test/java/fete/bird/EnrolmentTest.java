package fete.bird;

import fete.bird.feature.course.CourseRequest;
import fete.bird.feature.enrolment.EnrolmentRequest;
import fete.bird.feature.enrolment.EnrolmentResponse;
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
public class EnrolmentTest extends BaseFixture {
    private UUID lastCreatedCountryId;
    private final String enrolmentEndpoint = "/enrolment";

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s",enrolmentEndpoint, UUID.randomUUID()));
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
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        EnrolmentResponse createbody = response.body();
        //lastCreatedCountryId = createbody.id();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s",enrolmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, EnrolmentResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the enrolment by id")
    void shouldGetTheEnrolmentById() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        EnrolmentResponse createbody = response.body();
        //lastCreatedCountryId = createbody.id();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s",enrolmentEndpoint, createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,EnrolmentResponse.class);
        EnrolmentResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }

    @Test
    @DisplayName("Should get all the enrollments")
    void shouldGetAllTheEnrollments() {
        // Given
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        //lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET(enrolmentEndpoint);
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        var getbody = getResponse.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the enrolment")
    void shouldCreateTheEnrolment() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        // When
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        lastCreatedCountryId = response.body().id();
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", enrolmentEndpoint, UUID.randomUUID()), enrolmentRequest);
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(patchRequest);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw an un accepted entity on duplicate enrolment id")
    void shouldThrowAnUnAcceptedEntityOnDuplicateCountryName() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        lastCreatedCountryId = response.body().id();
        // When
        HttpClientResponseException exception = assertThrows(
                HttpClientResponseException.class,
                () -> httpClient.toBlocking().exchange(request)
        );
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Should update the enrolment")
    void shouldUpdateTheEnrolment() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var createRequest = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(createRequest, EnrolmentResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        CourseRequest courseToUpdateRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", enrolmentEndpoint, response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, EnrolmentResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the enrolment and fetch the updated value")
    void shouldUpdateTheEnrolmentAndFetchTheUpdatedValue() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var createRequest = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(createRequest, EnrolmentResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        EnrolmentRequest enrolmentToUpdateRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", enrolmentEndpoint, lastCreatedCountryId), enrolmentToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, EnrolmentResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s",enrolmentEndpoint, lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,EnrolmentResponse.class);
        EnrolmentResponse getbody = getResponse.body();
        assertEquals(getbody.studentId(), patchResponse.body().studentId());
    }


    @Test
    @DisplayName("Should delete the enrolment")
    void shouldDeleteTheEnrolment() {
        // Give
        EnrolmentRequest enrolmentRequest = testDataGenerator.generateRecord(EnrolmentRequest.class);
        var request = HttpRequest.POST(enrolmentEndpoint, enrolmentRequest);
        var response = httpClient.toBlocking().exchange(request, EnrolmentResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        EnrolmentResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s",enrolmentEndpoint, body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.OK);

    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the enrolment")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheEnrolment() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", enrolmentEndpoint, UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
