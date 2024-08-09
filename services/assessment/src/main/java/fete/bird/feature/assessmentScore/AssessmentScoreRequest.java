package fete.bird.feature.assessmentScore;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;
@Serdeable
public record AssessmentScoreRequest(UUID studentId, UUID questionId, Double score, String similarity) {
}
