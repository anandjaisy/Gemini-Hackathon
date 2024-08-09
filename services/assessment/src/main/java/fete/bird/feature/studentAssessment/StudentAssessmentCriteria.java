package fete.bird.feature.studentAssessment;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record StudentAssessmentCriteria(UUID studentId, UUID questionId) {
}
