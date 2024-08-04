package fete.bird.feature.question;

import fete.bird.feature.assessment.AssessmentResponse;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record QuestionResponse(UUID id, AssessmentResponse assessment, String question, String answer, Instant createdDate) {
}
