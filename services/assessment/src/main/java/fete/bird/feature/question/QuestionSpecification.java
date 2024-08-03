package fete.bird.feature.question;

import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class QuestionSpecification implements Predicate<Question> {
    private Optional<QuestionCriteria> criteria;
    public QuestionSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<QuestionCriteria> criteria) {
        this.criteria = criteria;
    }
    @Override
    public boolean test(Question question) {
        return this.criteria.isEmpty();
    }
}
