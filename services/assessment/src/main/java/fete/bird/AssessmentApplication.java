package fete.bird;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Assessment",
                version = "0.0"
        )
)
public class AssessmentApplication {

    public static void main(String[] args) {
        Micronaut.run(AssessmentApplication.class, args);
    }
}