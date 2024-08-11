package fete.bird.persistence;

import fete.bird.feature.assessment.Assessment;
import fete.bird.feature.question.Question;
import fete.bird.feature.studentAssessment.StudentAssessment;
import io.micronaut.core.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Root {
    private final Map<UUID, Assessment> assessment = new HashMap<>();
    private final Map<UUID, Question> question = new HashMap<>();
    private final Map<UUID, StudentAssessment> studentAssessment = new HashMap<>();

    @NonNull
    public Map<UUID, Assessment> getAssessment() {
        return this.assessment;
    }
    @NonNull
    public Map<UUID, Question> getQuestion() {
        return this.question;
    }
    @NonNull
    public Map<UUID, StudentAssessment> getStudentAssessment() {
        return this.studentAssessment;
    }
}
