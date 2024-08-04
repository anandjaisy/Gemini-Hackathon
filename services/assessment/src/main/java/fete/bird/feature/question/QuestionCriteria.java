package fete.bird.feature.question;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record QuestionCriteria(String assessmentId, String questionId) {
}
