package fete.bird.feature.assessment;

import jakarta.inject.Singleton;

import java.util.function.Function;

@Singleton
public record AssessmentResponseMapper() implements Function<Assessment, AssessmentResponse> {
    @Override
    public AssessmentResponse apply(Assessment assessment) {
        return new AssessmentResponse(assessment.id(),
                assessment.name(),
                assessment.description(),
                assessment.courseId(),
                assessment.assessmentDate(),
                assessment.assessmentDueDate());
    }
}
