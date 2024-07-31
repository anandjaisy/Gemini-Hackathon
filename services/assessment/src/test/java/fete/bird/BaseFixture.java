package fete.bird;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class BaseFixture implements TestPropertyProvider {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @TempDir
    static File tempDir;

    protected final TestDataGenerator testDataGenerator = new TestDataGenerator();

    @Override
    @NonNull
    public Map<String, String> getProperties() {
        return Collections.singletonMap(
                "microstream.storage.main.storage-directory", tempDir.getAbsolutePath()
        );
    }
}
