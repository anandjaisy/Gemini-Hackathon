package fete.bird.feature.assessment;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record Assessment(UUID id, String name, String description, UUID courseId, Instant createdDate, Instant dueDate) {
}
