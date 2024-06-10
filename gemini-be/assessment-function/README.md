## Micronaut 4.4.3 Documentation

- [User Guide](https://docs.micronaut.io/4.4.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.4.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.4.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

# Micronaut and Google Cloud Function

## Running the function locally

```cmd
./gradlew runFunction
```

And visit http://localhost:8080/assignment-function
## Deploying the function

First build the function with:

```bash
$ ./gradlew shadowJar
```

Then `cd` into the `build/libs` directory (deployment has to be done from the location where the JAR lives):

```bash
$ cd build/libs
```

Now run:

```bash
$ gcloud functions deploy assignment-function --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java17 --trigger-http
```

Choose unauthenticated access if you don't need auth.

To obtain the trigger URL do the following:

```bash
$ YOUR_HTTP_TRIGGER_URL=$(gcloud functions describe assignment-function --format='value(httpsTrigger.url)')
```

You can then use this variable to test the function invocation:

```bash
$ curl -i $YOUR_HTTP_TRIGGER_URL/assignment-function
```

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
## Feature eclipsestore documentation

- [Micronaut EclipseStore documentation](https://micronaut-projects.github.io/micronaut-eclipsestore/latest/guide)

- [https://docs.eclipsestore.io/](https://docs.eclipsestore.io/)


## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)


## Feature eclipsestore-rest documentation

- [Micronaut EclipseStore REST documentation](https://micronaut-projects.github.io/micronaut-eclipseStore/latest/guide/#rest)

- [https://docs.eclipsestore.io/manual/storage/rest-interface/index.html](https://docs.eclipsestore.io/manual/storage/rest-interface/index.html)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature google-cloud-function documentation

- [Micronaut Google Cloud Function documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#simpleFunctions)


## Feature google-cloud-function-http documentation

- [Micronaut Google Cloud Function documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#httpFunctions)


## Feature swagger-ui documentation

- [Micronaut Swagger UI documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://swagger.io/tools/swagger-ui/](https://swagger.io/tools/swagger-ui/)


