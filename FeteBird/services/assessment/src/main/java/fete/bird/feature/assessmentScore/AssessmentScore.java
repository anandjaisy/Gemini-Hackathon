package fete.bird.feature.assessmentScore;

import java.util.UUID;

public record AssessmentScore(UUID id, UUID studentId, Double score, UUID studentAssessmentId, String similarity) {
}
