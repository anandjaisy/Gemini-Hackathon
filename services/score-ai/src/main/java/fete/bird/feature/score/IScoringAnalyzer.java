package fete.bird.feature.score;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface IScoringAnalyzer {
        @UserMessage("""
            Extract the answer, suggestion, difference, percentageCorrect and grade of the student described below.
            Difference - this should highlight the worlds that matched with bold letter. It is a comparison between base answer and student answer
            Return this structure: {"answer": {{it}}, "suggestion": "This can be improved in this way", "difference": "", "percentageCorrect": "40%", "grade":"A+"}
            Return only JSON, without any markdown markup surrounding it.
            Here is the document describing the answer:
            ---
            {{it}}
            ---
            JSON:
            """)
    ScoreResponse score(String answer);
}