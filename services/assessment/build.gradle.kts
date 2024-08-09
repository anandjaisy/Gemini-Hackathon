plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.2"
    id("io.micronaut.aot") version "4.4.2"
}

version = "0.1"
group = "fete.bird"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.eclipsestore:micronaut-eclipsestore-annotations")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore-annotations")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.grpc:micronaut-grpc-client-runtime")

    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")

    implementation("com.auth0:java-jwt:4.4.0")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.eclipse.store:afs-sql")

    developmentOnly("io.micronaut.eclipsestore:micronaut-eclipsestore-rest")

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("com.h2database:h2")
    testImplementation("net.datafaker:datafaker:2.2.2")
}


application {
    mainClass = "fete.bird.Assessment"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("fete.bird.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}
// Can MicroStream handle Records?
//Yes, but due to reflection restrictions of records introduced in Java 15 an export has to be added to the VM parameters:
tasks.withType<JavaExec> {
    jvmArgs("--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED")
}
tasks.test {
    // Set JVM options for the test task
    jvmArgs("--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED")
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


