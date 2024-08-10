package fete.bird.common;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("gcp.project")
public record ConfigResource(String name, String location, String model, int retry) { }
