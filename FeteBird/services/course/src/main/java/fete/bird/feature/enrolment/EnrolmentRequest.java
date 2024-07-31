package fete.bird.feature.enrolment;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Optional;
import java.util.UUID;

@Serdeable
public record EnrolmentRequest(Optional<UUID> id, UUID courseId, Optional<UUID> professorId, Optional<UUID> studentId) { }
