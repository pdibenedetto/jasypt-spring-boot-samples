package demo.support;

import bootstrap.BootstrapApplication;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * JUnit 5 extension that runs the bootstrap Spring Boot application once (profile 'bootstrap')
 * to create the H2 database file, user, and seed data before the main application tests execute.
 */
public class BootstrapDbExtension implements BeforeAllCallback {
    private static boolean bootstrapped = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (bootstrapped) return;
        new SpringApplicationBuilder(BootstrapApplication.class)
                .profiles("bootstrap")
                .run()
                .close();
        // Clear any leftover system properties that might affect the main test context
        System.clearProperty("spring.application.name");
        System.clearProperty("LOGGED_APPLICATION_NAME");
        System.clearProperty("APPLICATION_NAME");
        // print all system properties
        // System.getProperties().forEach((k, v) -> System.out.printf("%s=%s%n", k, v));
        bootstrapped = true;
    }
}

