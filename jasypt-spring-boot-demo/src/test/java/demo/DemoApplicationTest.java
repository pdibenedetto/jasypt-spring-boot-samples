package demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@SetSystemProperty(key = "jasypt.encryptor.password", value = "password")
@SetEnvironmentVariable(key = "FOO_BAR", value = "ENC(nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+)")
public class DemoApplicationTest {

	@Autowired
	ConfigurableEnvironment environment;

	@Autowired
	MyService service;

	@Autowired
	ItemConfig itemConfig;

	@Autowired
    SimpleBean simpleBean;

	@Autowired
	@Qualifier("encryptorBean")
	StringEncryptor encryptor;



	@Test
	public void testEnvironmentProperties() {
		System.out.println(environment);
		assertEquals("chupacabras", environment.getProperty("secret.property"));
		assertEquals("chupacabras", environment.getProperty("secret2.property"));
		assertEquals("chupacabras", environment.getProperty("secret3.property"));
	}

	@Test
	public void testServiceProperties() {
		assertEquals("chupacabras", service.getSecret());
	}

    @Test
    public void testXMLProperties() {
        assertEquals("chupacabras", simpleBean.getValue());
    }

    @Test
    public void testConfigurationPropertiesProperties() {
        assertEquals("chupacabras", itemConfig.getPassword());
        assertEquals("my configuration", itemConfig.getConfigurationName());
        assertEquals(2, itemConfig.getItems().size());
        assertEquals("item1", itemConfig.getItems().get(0).getName());
        assertEquals(Integer.valueOf(1), itemConfig.getItems().get(0).getValue());
        assertEquals("item2", itemConfig.getItems().get(1).getName());
        assertEquals(Integer.valueOf(2), itemConfig.getItems().get(1).getValue());
    }

	@Test
	public void testEncryptDecrypt() {
		String message = "embedded-client";
		String encrypted = encryptor.encrypt(message);
		System.out.println("Encrypted Message: " + encrypted);
		assertEquals(message, encryptor.decrypt(encrypted));
	}

	@Test
	public void testEncryptedEnvironmentVariable() {
		assertEquals("chupacabras", environment.getProperty("FOO_BAR"));
        assertEquals("chupacabras", environment.getProperty("foo.bar"));
	}
}
