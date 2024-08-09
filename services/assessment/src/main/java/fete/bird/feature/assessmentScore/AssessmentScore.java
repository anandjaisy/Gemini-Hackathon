package fete.bird.feature.assessmentScore;

import java.util.UUID;

public record AssessmentScore(UUID id, UUID studentId, UUID questionId, Double score, String similarity) {
}
