package fete.bird.feature.enrolment;

import jakarta.inject.Singleton;

import java.util.function.Function;

@Singleton
public record RequestEnrolmentMapper() implements Function<EnrolmentRequest, Enrolment> {
    @Override
    public Enrolment apply(EnrolmentRequest request) {
        return new Enrolment(request.id().isPresent() ? request.id().get() : null,
                request.courseId(),
                request.professorId(),
                request.studentId());
    }
}
