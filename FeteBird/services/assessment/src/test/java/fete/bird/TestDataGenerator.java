package fete.bird;

import net.datafaker.Faker;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public class TestDataGenerator {
    private final Faker faker;

    public TestDataGenerator() {
        this.faker = new Faker();
    }

    public <T> T generateRecord(Class<T> recordClass) {
        try {
            if (recordClass.isRecord()) {
                var recordComponents = recordClass.getRecordComponents();
                Class<?>[] componentTypes = new Class<?>[recordComponents.length];
                for (int i = 0; i < recordComponents.length; i++) {
                    componentTypes[i] = recordComponents[i].getType();
                }
                Object[] args = new Object[recordComponents.length];
                for (int i = 0; i < recordComponents.length; i++) {
                    if (componentTypes[i].equals(UUID.class)) {
                        args[i] = UUID.randomUUID();
                    } else if (componentTypes[i].equals(Optional.class)) {
                        args[i] = Optional.of(UUID.randomUUID());
                    } else if (componentTypes[i].equals(String.class)) {
                        args[i] = faker.expression("#{letterify 'test????test'}");
                    }
                }
                Constructor<T> constructor = recordClass.getConstructor(componentTypes);
                return constructor.newInstance(args);
            } else {
                throw new IllegalArgumentException("Class is not a record");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to generate record", e);
        }
    }
}
