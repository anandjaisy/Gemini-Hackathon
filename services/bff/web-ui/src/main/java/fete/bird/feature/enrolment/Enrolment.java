package fete.bird.feature.enrolment;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record Enrolment(UUID id, UUID courseId, UUID professorId, UUID studentId) {
}
