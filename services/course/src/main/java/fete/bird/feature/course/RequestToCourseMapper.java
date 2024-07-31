package fete.bird.feature.course;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Singleton
public record RequestToCourseMapper() implements BiFunction<Optional<UUID>,CourseRequest, Course> {
    @Override
    public Course apply(Optional<UUID> requestId, CourseRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return new Course(id, request.name(), request.description());
    }
}
