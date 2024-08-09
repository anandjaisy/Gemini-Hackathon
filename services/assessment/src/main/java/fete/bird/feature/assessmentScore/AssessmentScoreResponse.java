package fete.bird.feature.assessmentScore;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record AssessmentScoreResponse(UUID id, UUID studentId, Double score, UUID questionId, String similarity) {
}
