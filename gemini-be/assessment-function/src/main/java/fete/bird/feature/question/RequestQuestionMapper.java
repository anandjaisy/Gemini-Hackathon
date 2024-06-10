package fete.bird.feature.question;

import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Singleton
public record RequestQuestionMapper() implements BiFunction<Optional<UUID>, QuestionRequest, Question> {
    @Override
    public Question apply(Optional<UUID> requestId, QuestionRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return new Question(id,
                request.assessmentId(),
                request.question(),
                request.answer());
    }
}
