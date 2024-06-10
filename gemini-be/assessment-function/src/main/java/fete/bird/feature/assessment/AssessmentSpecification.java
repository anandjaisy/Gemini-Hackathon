package fete.bird.feature.assessment;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class AssessmentSpecification implements Predicate<Assessment> {
    private Optional<AssessmentCriteria> criteria;
    public AssessmentSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<AssessmentCriteria> criteria) {
        this.criteria = criteria;
    }
    @Override
    public boolean test(Assessment assessment) {
        return false;
    }
}
