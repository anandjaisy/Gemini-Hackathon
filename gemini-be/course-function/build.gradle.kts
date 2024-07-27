plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.0"
    id("io.micronaut.aot") version "4.4.0"
    id("com.google.protobuf") version "0.9.2"
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

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore-annotations")

    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.eclipse.store:afs-sql")

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("com.h2database:h2")
    testImplementation("net.datafaker:datafaker:2.2.2")

    developmentOnly("io.micronaut.eclipsestore:micronaut-eclipsestore-rest")
}


application {
    mainClass = "fete.bird.CourseFunction"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
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


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


