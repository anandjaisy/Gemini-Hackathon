package fete.bird.feature.assessment.question;

import fete.bird.feature.assessment.AssessmentResponse;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record Question(UUID id, AssessmentResponse assessment, String question, String answer, String studentAnswer,
                       Instant createdDate, boolean isSubmittedByStudent, boolean isMarkedByTeacher) {
}
