package fete.bird.persistence;

import fete.bird.feature.assessmentEvaluation.AssessmentScore;
import io.micronaut.core.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Root {
    private final Map<UUID, AssessmentScore> assessmentScore = new HashMap<>();

    @NonNull
    public Map<UUID, AssessmentScore> getAssessmentScore() {
        return this.assessmentScore;
    }
}
