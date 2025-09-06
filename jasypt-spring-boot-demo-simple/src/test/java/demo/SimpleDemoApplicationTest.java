package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

@SetSystemProperty(key = "jasypt.encryptor.password", value = "password")
@SetSystemProperty(key = "ENCRYPTED_PASSWORD", value = "9ah+QnEdccHCkARkGZ7f0v5BLXXC+z0mr4hyjgE8T2G7mF75OBU1DgmC0YsGis8x")
@SpringBootTest(classes = SimpleDemoApplication.class)
@ContextConfiguration(loader = CustomEnvironmentContextLoader.class)
public class SimpleDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    MyService service;

    @Autowired
    EncryptablePropertyResolver resolver;

    @Autowired
    StringEncryptor encryptor;


    @Test
    public void testStringEncryptorIsPresent() {
        Assertions.assertNotNull(encryptor, "StringEncryptor should be present");
    }

    @Test
    public void testEnvironmentProperties() {
        Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
        Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
    }

	@Test
	public void testIndirectPropertiesDirectly() {
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
    public void testSkipRandomPropertySource() {
        Assertions.assertEquals(RandomValuePropertySource.class, Objects.requireNonNull(environment.getPropertySources().get("random")).getClass());
    }

}
