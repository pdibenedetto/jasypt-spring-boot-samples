package demo;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;

@SpringBootTest(classes = SimpleEmbeddedConfigServerAsymmetricDemoApplication.class)
@SetSystemProperty(key = "jasypt.encryptor.private-key-location", value = "classpath:privatekey.pem")
@SetSystemProperty(key = "jasypt.encryptor.public-key-location", value = "classpath:publickey.pem")
public class SimpleEmbeddedConfigServerAsymmetricDemoApplicationTest {

	@Autowired
	Environment environment;


	@Test
	public void testEnvironmentProperties() {
		Assertions.assertEquals("chupacabras", environment.getProperty("secret.property"));
		Assertions.assertEquals("chupacabras", environment.getProperty("secret2.property"));
	}

	@Test
	public void encryptProperty() throws Exception {
		SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
		config.setKeyFormat(PEM);
		config.setPrivateKeyLocation("classpath:privatekey.pem");
		config.setPublicKeyLocation("classpath:publickey.pem");
		StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
		String message = "embedded-client";
		String encrypted = encryptor.encrypt(message);
		System.out.printf("Encrypted message %s\n", encrypted);
		String decrypted = encryptor.decrypt(encrypted);
		Assertions.assertEquals(message, decrypted);
		System.out.println();
	}

	@Test
	public void testApplicationName() {
		Assertions.assertEquals("embedded-client", environment.getProperty("spring.application.name"));
	}

}
