package fete.bird.feature.assessmentEvaluation;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Singleton
public record RequestAssessmentScoreMapper() implements BiFunction<Optional<UUID>, AssessmentScoreRequest, AssessmentScore> {
    @Override
    public AssessmentScore apply(Optional<UUID> requestId, AssessmentScoreRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return new AssessmentScore(id,
                request.studentId(),
                request.questionId(),
                request.answer(),
                request.suggestion(),
                request.percentageMatched(),
                request.grade());
    }
}
