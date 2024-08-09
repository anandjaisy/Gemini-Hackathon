package fete.bird.feature.question;

import fete.bird.feature.assessment.AssessmentCriteria;
import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.assessment.AssessmentResponse;
import fete.bird.feature.studentAssessment.StudentAssessmentCriteria;
import fete.bird.feature.studentAssessment.StudentAssessmentRequest;
import fete.bird.feature.studentAssessment.StudentAssessmentResponse;
import fete.bird.shared.IRepository;
import fete.bird.shared.UserContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Singleton
public record QuestionResponseMapper(
        IRepository<AssessmentResponse, AssessmentRequest, AssessmentCriteria> iAssessmentRepository,
        UserContext userContext,
        IRepository<StudentAssessmentResponse, StudentAssessmentRequest, StudentAssessmentCriteria> studentAssessmentRepository) implements Function<Question, QuestionResponse> {
    @Override
    public QuestionResponse apply(Question question) {
        String studentId = userContext.getCurrentUserId();
        var studentAnswer = studentAssessmentRepository
                .find(Optional.of(new StudentAssessmentCriteria(UUID.fromString(studentId), question.id())))
                .stream().findFirst().orElse(null);
        return new QuestionResponse(question.id(),
                iAssessmentRepository.get(question.assessmentId()).orElse(null),
                question.question(),
                question.answer(),
                studentAnswer != null ? studentAnswer.answer() : null,
                question.createdDate(),
                question.isSubmitedByStudent(),
                question.isMarkedByTeacher()
        );
    }
}
