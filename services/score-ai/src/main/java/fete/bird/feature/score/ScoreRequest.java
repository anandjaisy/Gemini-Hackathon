package fete.bird.feature.score;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ScoreRequest(String baseQuestion, String baseAnswer, String answer) {
}
