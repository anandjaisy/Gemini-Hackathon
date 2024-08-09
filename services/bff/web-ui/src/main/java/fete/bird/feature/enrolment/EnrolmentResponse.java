package fete.bird.feature.enrolment;

import fete.bird.feature.course.Course;
import fete.bird.feature.user.User;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record EnrolmentResponse(UUID id, Course course, User professorUser, User studentUser) { }
