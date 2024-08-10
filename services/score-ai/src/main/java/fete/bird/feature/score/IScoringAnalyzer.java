package fete.bird.feature.score;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface IScoringAnalyzer {
    //    @UserMessage("""
//            Extract the answer, suggestion and mark of the score response described below.
//            Return a JSON document with a "answer", "suggestion" and "mark" property, \
//            following this structure: {"answer": "Photosysthesis is a process", "suggestion": 34, "mark": 90%}
//            Return only JSON, without any markdown markup surrounding it.
//            Here is the document describing the person:
//            ---
//            {{it}}
//            ---
//            JSON:
//            """)
    @UserMessage("""
            The student answer is provided in the {{answer}}
            """)
    String score(@V("answer") String answer);
}