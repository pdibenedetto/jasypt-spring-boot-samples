package demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;

@SetSystemProperty(key = "jasypt.encryptor.password", value = "password")
@SetEnvironmentVariable(key = "FOO_BAR", value = "ENC(DbG1GppXOsFa2G69PnmADvQFI3esceEhJYbaEIKCcEO5C85JEqGAhfcjFMGnoRFf)")
@SetSystemProperty(key = "ENCRYPTED_PASSWORD", value = "9ah+QnEdccHCkARkGZ7f0v5BLXXC+z0mr4hyjgE8T2G7mF75OBU1DgmC0YsGis8x")
@SpringBootTest(classes = CustomEnvironmentSimpleDemoApplication.class)
@ContextConfiguration(loader = CustomEnvironmentContextLoader.class)
public class CustomEnvironmentSimpleDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    MyService service;


    @Test
    public void testEnvironmentProperties() {
        Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
        Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
    }

    @Test
    public void testIndirectProperties() {
        Assertions.assertEquals("chupacabras", environment.getProperty("indirect.secret.property"));
        Assertions.assertEquals("chupacabras", environment.getProperty("indirect.secret.property2"));
        Assertions.assertEquals("https://uli:chupacabras@localhost:30000", environment.getProperty("endpoint"));
    }

    @Test
    public void testServiceProperties() {
        Assertions.assertEquals("chupacabras", service.getSecret());
        Assertions.assertEquals("chupacabras", service.getSecret2());
    }

    @Test
    public void testEncryptedEnvironmentVariable() {
        Assertions.assertEquals("chupacabras", environment.getProperty("FOO_BAR"));
        Assertions.assertEquals("chupacabras", environment.getProperty("foo.bar"));
    }

}
