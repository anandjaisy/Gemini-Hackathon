package fete.bird.feature.question;

import fete.bird.shared.TriFunction;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Singleton
public record RequestQuestionMapper() implements TriFunction<Optional<UUID>, QuestionRequest, Optional<Question>, Question> {
    @Override
    public Question apply(Optional<UUID> requestId, QuestionRequest request, Optional<Question> dbQuestion) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return dbQuestion.map(existingQuestion -> mergeQuestionData(id, request, existingQuestion))
                .orElseGet(() -> createNewQuestion(id, request));
    }

    private Question mergeQuestionData(UUID id, QuestionRequest request, Question existingQuestion) {
        UUID assessmentId = (request.assessmentId() != null) ? request.assessmentId() : existingQuestion.assessmentId();
        String question = (request.question() != null && !request.question().isBlank()) ? request.question() : existingQuestion.question();
        String answer = (request.answer() != null && !request.answer().isBlank()) ? request.answer() : existingQuestion.answer();
        Instant createdDate = (existingQuestion.createdDate() != null) ? existingQuestion.createdDate() : Instant.now();
        boolean isSubmittedByStudent = (request.isSubmitedByStudent().isPresent()) ? request.isSubmitedByStudent().get() : existingQuestion.isSubmitedByStudent();
        boolean isMarkedByTeacher = (request.isMarkedByTeacher().isPresent()) ? request.isMarkedByTeacher().get() : existingQuestion.isMarkedByTeacher();
        return new Question(id, assessmentId, question, answer, createdDate, isSubmittedByStudent, isMarkedByTeacher);
    }

    private Question createNewQuestion(UUID id, QuestionRequest request) {
        return new Question(id, request.assessmentId(), request.question(), request.answer(), Instant.now(), false, false);
    }
}
