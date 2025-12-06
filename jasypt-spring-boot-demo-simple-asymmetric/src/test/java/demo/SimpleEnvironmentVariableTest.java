package demo;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

/**
 * Simple test to investigate JUnit Pioneer's environment variable mechanism
 * without Spring Boot context interference.
 */
public class SimpleEnvironmentVariableTest {

    @Test
    @SetEnvironmentVariable(key = "TEST_ENV_VAR", value = "test_value")
    public void investigateJUnitPioneerMechanism() {
        System.out.println("=== JUnit Pioneer Environment Variable Investigation ===");

        // 1. Check direct environment variable access
        String directEnvVar = System.getenv("TEST_ENV_VAR");
        System.out.println("1. Direct System.getenv('TEST_ENV_VAR'): " + directEnvVar);

        // 2. Check system properties (in case JUnit Pioneer sets it there too)
        String sysProp = System.getProperty("TEST_ENV_VAR");
        System.out.println("2. System.getProperty('TEST_ENV_VAR'): " + sysProp);

        // 3. Check execution context
        String javaCommand = System.getProperty("sun.java.command");
        System.out.println("3. Java Command: " + javaCommand);

        boolean isMaven = javaCommand != null && (javaCommand.contains("surefire") || javaCommand.contains("maven"));
        System.out.println("4. Is Maven execution: " + isMaven);

        // 4. Show all environment variables that contain our test key
        System.out.println("5. All environment variables containing 'TEST':");
        System.getenv().entrySet().stream()
            .filter(entry -> entry.getKey().contains("TEST"))
            .forEach(entry -> System.out.println("   " + entry.getKey() + " = " + entry.getValue()));

        System.out.println("=== END INVESTIGATION ===");
    }
}
