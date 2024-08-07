package fete.bird.feature.enrolment;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class EnrolmentSpecification implements Predicate<Enrolment> {
    private Optional<EnrolmentCriteria> criteria;
    public EnrolmentSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<EnrolmentCriteria> criteria) {
        this.criteria = criteria;
    }
    @Override
    public boolean test(Enrolment professorEnrolment) {
        return true;
    }
}
