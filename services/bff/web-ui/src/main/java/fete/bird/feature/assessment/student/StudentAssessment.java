package fete.bird.feature.assessment.student;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record StudentAssessment(UUID id, UUID studentId, UUID questionId, String answer) {
}
