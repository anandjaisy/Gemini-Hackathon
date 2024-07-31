package fete.bird.feature.enrolment;

import jakarta.inject.Singleton;
import java.util.function.Function;

@Singleton
public record EnrolmentResponseMapper() implements Function<Enrolment, EnrolmentResponse> {
    @Override
    public EnrolmentResponse apply(Enrolment enrolment) {
        return new EnrolmentResponse(enrolment.id(), enrolment.courseId(), enrolment.studentId(), enrolment.studentId());
    }
}
