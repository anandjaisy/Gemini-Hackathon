package fete.bird.feature.question;

import jakarta.inject.Singleton;
import java.util.function.Function;

@Singleton
public record QuestionResponseMapper() implements Function<Question, QuestionResponse> {
    @Override
    public QuestionResponse apply(Question question) {
        return new QuestionResponse(question.id(),
                question.assessmentId(),
                question.question(),
                question.answer());
    }
}
