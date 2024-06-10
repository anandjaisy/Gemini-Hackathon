package fete.bird.feature.course;

import jakarta.inject.Singleton;
import java.util.function.Function;

@Singleton
public record CourseToResponseMapper() implements Function<Course, CourseResponse> {
    @Override
    public CourseResponse apply(Course course) {
        return new CourseResponse(course.id(), course.name(), course.description());
    }
}
