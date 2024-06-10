package fete.bird.feature.course;

import fete.bird.shared.IController;
import fete.bird.shared.IRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/course")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Course")
public record CourseController(IRepository<CourseResponse,CourseRequest, CourseCriteria> courseRepository) implements IController<CourseResponse,CourseRequest, CourseCriteria> {
    @Override
    public Optional<CourseResponse> get(UUID id) {
        return courseRepository.get(id);
    }

    @Override
    public List<CourseResponse> find(Optional<CourseCriteria> criteria) {
        return courseRepository.find(criteria);
    }

    @Override
    public Optional<CourseResponse> create(CourseRequest request) {
        return courseRepository.create(request);
    }

    @Override
    public Optional<CourseResponse> update(UUID id, CourseRequest request) {
        return courseRepository.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        courseRepository.delete(id);
    }
}
