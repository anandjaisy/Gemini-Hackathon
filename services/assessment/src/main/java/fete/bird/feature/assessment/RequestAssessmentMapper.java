package fete.bird.feature.assessment;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Singleton
public record RequestAssessmentMapper() implements BiFunction<Optional<UUID>,AssessmentRequest, Assessment> {
    @Override
    public Assessment apply(Optional<UUID> requestId, AssessmentRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return new Assessment(id,
                request.name(),
                request.description(),
                request.courseId(),
                request.assessmentDate(),
                request.assessmentDueDate());
    }
}
