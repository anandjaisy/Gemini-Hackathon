package fete.bird.feature.enrolment;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Optional;
import java.util.UUID;

@Serdeable
public record EnrolmentRequest(UUID courseId, UUID professorId, UUID studentId) { }
