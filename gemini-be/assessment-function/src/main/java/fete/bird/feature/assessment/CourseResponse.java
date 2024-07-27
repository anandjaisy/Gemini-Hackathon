package fete.bird.feature.assessment;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record CourseResponse(UUID id, String name, String description) { }
