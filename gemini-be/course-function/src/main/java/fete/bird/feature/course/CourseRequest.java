package fete.bird.feature.course;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Optional;
import java.util.UUID;

@Serdeable
public record CourseRequest(String name, String description) {
}
