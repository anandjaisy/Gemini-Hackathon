package fete.bird.feature.studentAssessment;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;
@Serdeable
public record StudentAssessmentResponse(UUID id, UUID studentId, UUID questionId, String answer) {
}
