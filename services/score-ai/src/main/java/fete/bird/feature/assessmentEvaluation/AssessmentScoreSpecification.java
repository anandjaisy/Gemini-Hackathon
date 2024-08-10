package fete.bird.feature.assessmentEvaluation;

import fete.bird.common.Predicate;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class AssessmentScoreSpecification implements Predicate<List<AssessmentScore>> {
    private Optional<AssessmentScoreCriteria> criteria;

    public AssessmentScoreSpecification() {
        this.criteria = Optional.empty();
    }

    public AssessmentScoreSpecification setCriteria(Optional<AssessmentScoreCriteria> criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public List<AssessmentScore> apply(List<AssessmentScore> assessments) {
        if (criteria.isEmpty()) {
            return assessments; // No criteria, return the full list
        }
        AssessmentScoreCriteria c = criteria.get();
        return assessments.stream()
                .filter(studentAssessment -> {
                    boolean matches = true;
                    if (c.questionId() != null) {
                        matches = c.questionId().toString().equals(studentAssessment.questionId().toString());
                    }
                    return matches;
                }).toList();
    }
}
