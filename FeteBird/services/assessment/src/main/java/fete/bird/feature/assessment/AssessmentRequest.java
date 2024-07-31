package fete.bird.feature.assessment;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;
@Serdeable
public record AssessmentRequest(String name, String description, UUID courseId, Instant assessmentDate, Instant assessmentDueDate) {
}
