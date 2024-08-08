package fete.bird.shared;

import io.micronaut.http.context.ServerRequestContext;
import jakarta.inject.Singleton;

@Singleton
public record UserContext() {
    public String getCurrentUserId() {
        return ServerRequestContext.currentRequest()
                .flatMap(req -> req.getAttribute("userId", String.class))
                .orElse(null);
    }
}
