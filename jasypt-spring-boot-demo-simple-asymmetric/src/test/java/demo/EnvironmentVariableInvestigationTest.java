package demo;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 * Test to investigate the root cause of environment variable handling differences
 * between IDE and Maven execution environments.
 */
@SpringBootTest(classes = SimpleAsymmetricDemoApplication.class)
@SetEnvironmentVariable(key = "TEST_ENV_VAR", value = "test_value")
public class EnvironmentVariableInvestigationTest {

    @Autowired
    Environment environment;

    @Test
    public void investigateEnvironmentVariableHandling() {
        System.out.println("=== INVESTIGATION: Environment Variable Handling ===");

        // 1. Check direct environment variable access
        String directEnvVar = System.getenv("TEST_ENV_VAR");
        System.out.println("1. Direct System.getenv('TEST_ENV_VAR'): " + directEnvVar);

        // 2. Check property source details
        System.out.println("\n2. Property Sources Analysis:");
        ConfigurableEnvironment configEnv = (ConfigurableEnvironment) environment;
        for (PropertySource<?> ps : configEnv.getPropertySources()) {
            System.out.println("   - " + ps.getName() + " (" + ps.getClass().getSimpleName() + ")");
            if (ps.getName().contains("systemEnvironment")) {
                System.out.println("     System Environment PropertySource detected!");
                // Check if our test env var is in this property source
                if (ps.containsProperty("TEST_ENV_VAR")) {
                    System.out.println("     Contains TEST_ENV_VAR: " + ps.getProperty("TEST_ENV_VAR"));
                }
            }
        }

        // 3. Try different property name variations
        System.out.println("\n3. Property Name Variations:");
        String[] testKeys = {
            "TEST_ENV_VAR",
            "test.env.var",
            "test.envVar",
            "test-env-var"
        };

        for (String key : testKeys) {
            String value = environment.getProperty(key);
            System.out.println("   " + key + " = " + value);
        }

        // 4. Check execution context
        System.out.println("\n4. Execution Context:");
        String javaCommand = System.getProperty("sun.java.command");
        System.out.println("   Java Command: " + javaCommand);

        boolean isMaven = javaCommand != null && (javaCommand.contains("surefire") || javaCommand.contains("maven"));
        boolean isIDE = javaCommand != null && !isMaven;

        System.out.println("   Is Maven: " + isMaven);
        System.out.println("   Is IDE: " + isIDE);

        // 5. Check JUnit Pioneer mechanism
        System.out.println("\n5. JUnit Pioneer Investigation:");
        System.out.println("   Environment variable set by @SetEnvironmentVariable");
        System.out.println("   JUnit Pioneer version in use: Check if it modifies System.getenv() vs PropertySources");

        System.out.println("\n=== END INVESTIGATION ===");
    }
}
