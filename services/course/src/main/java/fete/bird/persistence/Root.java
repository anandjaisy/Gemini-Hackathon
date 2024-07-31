package fete.bird.persistence;

import fete.bird.feature.course.Course;
import fete.bird.feature.enrolment.Enrolment;
import io.micronaut.core.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Root {
    private final Map<UUID, Course> course = new HashMap<>();
    private final Map<UUID, Enrolment> enrolment = new HashMap<>();

    @NonNull
    public Map<UUID, Course> getCourse() {
        return this.course;
    }
    @NonNull
    public Map<UUID, Enrolment> getEnrolment() {
        return this.enrolment;
    }
}
