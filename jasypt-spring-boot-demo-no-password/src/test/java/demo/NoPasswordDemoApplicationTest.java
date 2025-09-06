package demo;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

//@SetSystemProperty(key = "jasypt.encryptor.password", value = "password")
@SpringBootTest(classes = NoPasswordDemoApplication.class)
public class NoPasswordDemoApplicationTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Autowired
    EncryptablePropertyResolver resolver;


    @Test
    public void testUsername() {
        Assertions.assertEquals("uli", environment.getProperty("test.user"));
    }
}
