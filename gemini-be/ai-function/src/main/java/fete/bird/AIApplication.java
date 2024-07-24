package fete.bird;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
    info = @Info(
            title = "ai-function",
            version = "0.0"
    )
)
public class AIApplication {

    public static void main(String[] args) {
        Micronaut.run(AIApplication.class, args);
    }
}