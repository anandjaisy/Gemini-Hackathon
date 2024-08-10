package fete.bird.feature.assessmentEvaluation;

import java.util.UUID;

public record AssessmentScore(UUID id, UUID studentId, UUID questionId, String answer, String suggestion, String percentageMatched, String grade) {
}
