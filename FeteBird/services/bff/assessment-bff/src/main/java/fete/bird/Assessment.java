package fete.bird;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record Assessment(UUID id, String name, String description, UUID courseId, Instant assessmentDate, Instant assessmentDueDate) {
}
