package fete.bird.feature.question;

import fete.bird.feature.assessment.AssessmentCriteria;
import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.assessment.AssessmentResponse;
import fete.bird.feature.studentAssessment.StudentAssessmentCriteria;
import fete.bird.feature.studentAssessment.StudentAssessmentRequest;
import fete.bird.feature.studentAssessment.StudentAssessmentResponse;
import fete.bird.shared.IRepository;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.function.Function;

@Singleton
public record QuestionResponseMapper(
        IRepository<AssessmentResponse, AssessmentRequest, AssessmentCriteria> iAssessmentRepository
        ) implements Function<Question, QuestionResponse> {
    @Override
    public QuestionResponse apply(Question question) {
        //String studentAnswer = studentAssessmentRepository.find(Optional.of(new StudentAssessmentCriteria(question.id(), question.id()))).getFirst().answer();
        return new QuestionResponse(question.id(),
                iAssessmentRepository.get(question.assessmentId()).orElse(null),
                question.question(),
                question.answer(),
                "studentAnswer",
                question.createdDate(),
                question.isSubmitedByStudent(),
                question.isMarkedByTeacher()
                );
    }
}
