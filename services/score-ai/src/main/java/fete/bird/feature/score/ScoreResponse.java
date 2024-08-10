package fete.bird.feature.score;

import dev.langchain4j.model.output.structured.Description;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ScoreResponse(@Description("The answer provided by student") String answer,
                            @Description("Suggestion provided by the professor") String suggestion,
                            @Description("Suggestion provided by the professor") String percentageMatched,
                            @Description("Marking done by the LLM with percentage") String grade) {
}
