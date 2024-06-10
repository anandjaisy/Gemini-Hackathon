package fete.bird.feature.course;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record CourseResponse(UUID id, String title, String description) {
}
