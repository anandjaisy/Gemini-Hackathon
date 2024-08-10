package fete.bird.feature.assessmentEvaluation;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record AssessmentScoreResponse(UUID id, UUID studentId, UUID questionId, String answer, String suggestion, String percentageMatched, String grade) {
}
