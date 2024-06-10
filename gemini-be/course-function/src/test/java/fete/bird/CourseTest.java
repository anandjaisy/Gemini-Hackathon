package fete.bird;

import fete.bird.feature.course.CourseRequest;
import fete.bird.feature.course.CourseResponse;
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
public class CourseTest extends BaseFixture {
    private UUID lastCreatedCountryId;

    @Test
    @DisplayName("Should throw the status 404 not found on invalid id")
    void shouldThrowTheStatus404NotFoundOnInvalidId() {
        // Give
        var request = HttpRequest.GET(String.format("%s/%s","/course", UUID.randomUUID()));
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
        CourseRequest requestModel = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", requestModel);
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        CourseResponse createbody = response.body();
        //lastCreatedCountryId = createbody.id();

        // When
        var getRequest = HttpRequest.GET(String.format("%s/%s","/course", createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest, CourseResponse.class);
        //Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should get the course by id")
    void shouldGetTheCourseById() {
        // Give
        CourseRequest requestModel = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", requestModel);
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        CourseResponse createbody = response.body();
        //lastCreatedCountryId = createbody.id();

        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // when
        var getRequest = HttpRequest.GET(String.format("%s/%s","/course", createbody.id()));
        var getResponse = httpClient.toBlocking().exchange(getRequest,CourseResponse.class);
        CourseResponse getbody = response.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
        assertEquals(createbody.id(), getbody.id());
    }

    @Test
    @DisplayName("Should get all course")
    void shouldGetAllCourse() {
        // Given
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", courseRequest);
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
        //lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        var getRequest = HttpRequest.GET("/course");
        var getResponse = httpClient.toBlocking().exchange(getRequest);
        var getbody = getResponse.body();
        // Then
        assertEquals(getResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Should create the course")
    void shouldCreateTheCourse() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", courseRequest);
        // When
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
        lastCreatedCountryId = response.body().id();
        //Then
        assertEquals(response.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should throw an exception as 404 not found if invalid id is passed")
    void shouldThrowAnExceptionAs404NotFoundIfInvalidIdIsPassed() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", "/course", UUID.randomUUID()), courseRequest);
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(patchRequest);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should throw an un accepted entity on duplicate course name")
    void shouldThrowAnUnAcceptedEntityOnDuplicateCountryName() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", courseRequest);
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
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
    @DisplayName("Should update the course")
    void shouldUpdateTheCourse() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var createRequest = HttpRequest.POST("/course", courseRequest);
        var response = httpClient.toBlocking().exchange(createRequest, CourseResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        CourseRequest courseToUpdateRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", "/course", response.body().id()), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, CourseResponse.class);
        //Then
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Should update the course and fetch the updated value")
    void shouldUpdateTheCourseAndFetchTheUpdatedValue() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var createRequest = HttpRequest.POST("/course", courseRequest);
        var response = httpClient.toBlocking().exchange(createRequest, CourseResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        // When
        CourseRequest courseToUpdateRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var patchRequest = HttpRequest.PATCH(String.format("%s/%s", "/course", lastCreatedCountryId), courseToUpdateRequest);
        var patchResponse = httpClient.toBlocking().exchange(patchRequest, CourseResponse.class);
        assertEquals(patchResponse.getStatus(), HttpStatus.ACCEPTED);
        //Then
        var getRequest = HttpRequest.GET(String.format("%s/%s","/course", lastCreatedCountryId));
        var getResponse = httpClient.toBlocking().exchange(getRequest,CourseResponse.class);
        CourseResponse getbody = getResponse.body();
        assertEquals(getbody.title(), patchResponse.body().title());
    }

    @Test
    @DisplayName("Should delete the course")
    void shouldDeleteTheCourse() {
        // Give
        CourseRequest courseRequest = testDataGenerator.generateRecord(CourseRequest.class);
        var request = HttpRequest.POST("/course", courseRequest);
        var response = httpClient.toBlocking().exchange(request, CourseResponse.class);
        lastCreatedCountryId = response.body().id();
        assertEquals(response.getStatus(), HttpStatus.CREATED);
        CourseResponse body = response.body();

        // When
        var deleteRequest = HttpRequest.DELETE(String.format("%s/%s","/course", body.id()));
        var deleteResponse = httpClient.toBlocking().exchange(deleteRequest);

        // Then
        assertEquals(deleteResponse.getStatus(), HttpStatus.OK);

    }

    @Test
    @DisplayName("Should throw an exception when invalid id is passed to delete the course")
    void shouldThrowAnExceptionWhenInvalidIdIsPassedToDeleteTheCourse() {
        // Give
        var request = HttpRequest.DELETE(String.format("%s/%s", "/course", UUID.randomUUID()));
        // When
        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking().exchange(request);
        });
        //Then
        assertEquals(httpClientResponseException.getStatus(), HttpStatus.NOT_FOUND);
    }
}
