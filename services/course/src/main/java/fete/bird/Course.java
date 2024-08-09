package fete.bird;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Course",
                version = "0.0"
        )
)
public class Course {

    public static void main(String[] args) {
        Micronaut.run(Course.class, args);
    }
}