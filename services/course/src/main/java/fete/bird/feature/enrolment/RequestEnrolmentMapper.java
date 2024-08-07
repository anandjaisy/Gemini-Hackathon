package fete.bird.feature.enrolment;

import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Singleton
public record RequestEnrolmentMapper() implements BiFunction<Optional<UUID>, EnrolmentRequest, Enrolment> {
    @Override
    public Enrolment apply(Optional<UUID> requestId, EnrolmentRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
                return new Enrolment(id,
                request.courseId(),
                request.professorId(),
                request.studentId());
    }
}
