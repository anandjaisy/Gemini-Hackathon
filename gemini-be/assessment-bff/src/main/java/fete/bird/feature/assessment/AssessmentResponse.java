package fete.bird.feature.assessment;

import fete.bird.feature.course.Course;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record AssessmentResponse(UUID id, String name, String description, Course course, Instant assessmentDate, Instant assessmentDueDate) {
}
