package fete.bird.feature.assessment.student;

import fete.bird.feature.user.User;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record StudentAssessmentResponse(User user, StudentAssessment question) {
}
