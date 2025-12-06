package demo;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SimpleEmbeddedConfigServerAsymmetricDemoApplication.class)
@SetSystemProperty(key = "jasypt.encryptor.private-key-location", value = "classpath:privatekey.pem")
@SetSystemProperty(key = "jasypt.encryptor.public-key-location", value = "classpath:publickey.pem")
@SetEnvironmentVariable(key = "FOO_BAR", value = "ENC(CP3WlGExtEbfdRxdjlJmcZn126RHoKXDCLBaUdHV4vWAU/kd/sqyP/fai7Auh1s3qR76KMxDNjlV/IioWuAO6CaMANcxIYhyPGsUCzixd6MsAUJLg+TjGGPV3y/l935cSw3So9B6kfN6hyuMyCV9WQR0UkvBjT+dC9Ur+PjcYAdDD5jzqBq32kuKed8V49jy0bGt5ER53hiKmMFHJbdi6JfkO0u6YoNlAp0fxDbiPR9VyoOuCrxjvl4/u8X4i/qZV01CRMKTPIlh54qB/C6c1Zh2s8Gscj6dLYrjyAoAz3C0QmKOMbCd2XLFgGIHKOpkiR3G2hDL9o4fRVcoO/2xDA==)")
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

    @Test
    public void testEncryptedEnvironmentVariable() {
        assertEquals("chupacabras", environment.getProperty("FOO_BAR"));
        assertEquals("chupacabras", environment.getProperty("foo.bar"));
    }


}
