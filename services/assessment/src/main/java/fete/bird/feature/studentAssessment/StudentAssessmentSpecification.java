package fete.bird.feature.studentAssessment;

import fete.bird.shared.Predicate;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class StudentAssessmentSpecification implements Predicate<List<StudentAssessment>> {

    private Optional<StudentAssessmentCriteria> criteria;

    public StudentAssessmentSpecification() {
        this.criteria = Optional.empty();
    }

    public StudentAssessmentSpecification setCriteria(Optional<StudentAssessmentCriteria> criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public List<StudentAssessment> apply(List<StudentAssessment> assessments) {
        if (criteria.isEmpty()) {
            return assessments; // No criteria, return the full list
        }
        StudentAssessmentCriteria c = criteria.get();
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
