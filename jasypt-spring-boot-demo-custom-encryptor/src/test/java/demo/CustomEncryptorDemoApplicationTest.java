package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SetSystemProperty(key = "jasypt.encryptor.password", value = "password")
@SpringBootTest(classes = CustomEncryptorDemoApplication.class)
public class CustomEncryptorDemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired(required = false)
	EncryptablePropertyResolver resolver;


	@Test
	public void testEnvironmentProperties() {
		assertEquals("chupacabras", environment.getProperty("secret.property"));
	}
}
