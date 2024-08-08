package fete.bird.feature.question;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record Question(UUID id, UUID assessmentId, String question, String answer, Instant createdDate, boolean isSubmitedByStudent, boolean isMarkedByTeacher) { }
