package fete.bird.feature.studentAssessment;

import java.util.UUID;

public record StudentAssessment(UUID id, UUID studentId, UUID questionId, String answer) {
}
