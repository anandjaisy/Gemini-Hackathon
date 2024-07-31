package fete.bird.feature.studentAssessment;

import jakarta.inject.Singleton;

import java.util.function.Function;

@Singleton
public record StudentAssessmentResponseMapper() implements Function<StudentAssessment, StudentAssessmentResponse> {
    @Override
    public StudentAssessmentResponse apply(StudentAssessment studentAssessment) {
        return new StudentAssessmentResponse(studentAssessment.id(),
                studentAssessment.studentId(),
                studentAssessment.questionId(),
                studentAssessment.answer());
    }
}
