package fete.bird.feature.studentAssessment;

import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class StudentAssessmentSpecification implements Predicate<StudentAssessment> {
    private Optional<StudentAssessmentCriteria> criteria;
    public StudentAssessmentSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<StudentAssessmentCriteria> criteria) {
        this.criteria = criteria;
    }
    @Override
    public boolean test(StudentAssessment studentAssessment) {
        return criteria.map(c -> {
            boolean matches = true;
            if (c.questionId() != null) {
                matches = c.questionId().toString().equals(studentAssessment.questionId().toString());
            }

            if (c.questionId() != null) {
                matches = matches && c.questionId().toString().equals(studentAssessment.studentId().toString());
            }
            return matches;
        }).orElse(true);
    }
}
