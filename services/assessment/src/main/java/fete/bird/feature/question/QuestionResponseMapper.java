package fete.bird.feature.question;

import fete.bird.feature.assessment.AssessmentCriteria;
import fete.bird.feature.assessment.AssessmentRequest;
import fete.bird.feature.assessment.AssessmentResponse;
import fete.bird.shared.IRepository;
import jakarta.inject.Singleton;
import java.util.function.Function;

@Singleton
public record QuestionResponseMapper(
        IRepository<AssessmentResponse, AssessmentRequest, AssessmentCriteria> iAssessmentRepository) implements Function<Question, QuestionResponse> {
    @Override
    public QuestionResponse apply(Question question) {
        return new QuestionResponse(question.id(),
                iAssessmentRepository.get(question.assessmentId()).orElse(null),
                question.question(),
                question.answer(),
                question.createdDate());
    }
}
