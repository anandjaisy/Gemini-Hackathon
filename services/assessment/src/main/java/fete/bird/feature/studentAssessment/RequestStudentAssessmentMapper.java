package fete.bird.feature.studentAssessment;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Singleton
public record RequestStudentAssessmentMapper() implements BiFunction<Optional<UUID>, StudentAssessmentRequest, StudentAssessment> {
    @Override
    public StudentAssessment apply(Optional<UUID> requestId, StudentAssessmentRequest request) {
        UUID id = requestId.orElseGet(UUID::randomUUID);
        return new StudentAssessment(id,
                request.studentId(),
                request.questionId(),
                request.answer());
    }
}
