package fete.bird.feature.course;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record Course(UUID id, String name, String description) {
}
