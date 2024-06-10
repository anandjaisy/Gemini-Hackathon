package fete.bird;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
    info = @Info(
            title = "Course",
            version = "0.0"
    )
)
public class CourseFunction {

    public static void main(String[] args) {
        Micronaut.run(CourseFunction.class, args);
    }
}