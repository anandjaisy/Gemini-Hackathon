package fete.bird.feature.user;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record User(UUID id, String username, String firstName, String lastName, String email) {
}
