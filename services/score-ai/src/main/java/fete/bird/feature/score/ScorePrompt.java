package fete.bird.feature.score;

import dev.langchain4j.service.UserMessage;

public interface ScorePrompt {
    @UserMessage("")
    ScoreResponse evaluate(String context);
}
