package fete.bird.feature.assessmentScore;

import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class AssessmentScoreSpecification implements Predicate<AssessmentScore> {
    private Optional<AssessmentScoreCriteria> criteria;
    public AssessmentScoreSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<AssessmentScoreCriteria> criteria) {
        this.criteria = criteria;
    }
    @Override
    public boolean test(AssessmentScore assessmentScore) {
        return true;
    }
}
