package fete.bird.feature.assessmentEvaluation;

import jakarta.inject.Singleton;

import java.util.function.Function;

@Singleton
public record AssessmentScoreResponseMapper() implements Function<AssessmentScore, AssessmentScoreResponse> {
    @Override
    public AssessmentScoreResponse apply(AssessmentScore assessmentScore) {
        return new AssessmentScoreResponse(assessmentScore.id(),
                assessmentScore.studentId(),
                assessmentScore.questionId(),
                assessmentScore.answer(),
                assessmentScore.suggestion(),
                assessmentScore.percentageMatched(),
                assessmentScore.grade());
    }
}
