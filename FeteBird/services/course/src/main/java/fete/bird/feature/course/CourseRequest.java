package fete.bird.feature.course;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record CourseRequest(String name, String description) {
}
