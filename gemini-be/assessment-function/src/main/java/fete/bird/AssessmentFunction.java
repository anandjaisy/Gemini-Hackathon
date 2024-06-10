package fete.bird;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
    info = @Info(
            title = "Assessment",
            version = "0.0"
    )
)
public class AssessmentFunction {

    public static void main(String[] args) {
        Micronaut.run(AssessmentFunction.class, args);
    }
}