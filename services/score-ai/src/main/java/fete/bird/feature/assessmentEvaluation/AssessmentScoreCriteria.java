package fete.bird.feature.assessmentEvaluation;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record AssessmentScoreCriteria(UUID studentId, UUID questionId) {
}
