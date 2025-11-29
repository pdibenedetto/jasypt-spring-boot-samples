package demo;


import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableServletEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Sample Boot application that showcases easy integration of Jasypt encryption by
 * simply adding {@literal @EnableEncryptableProperties} to any Configuration class.
 * For decryption a password is required and is set through system properties in this example,
 * but it could be passed command line argument too like this: --jasypt.encryptor.password=password
 *
 * @author Ulises Bocchio
 */
@SpringBootApplication
@EnableConfigServer
//Uncomment this if not using jasypt-spring-boot-starter (use jasypt-spring-boot) dependency in pom instead
public class SimpleEmbeddedConfigServerAsymmetricDemoApplication implements CommandLineRunner {

    static {
        System.setProperty("jasypt.encryptor.private-key-location", "classpath:privatekey.pem");
    }

    private static final Logger LOG = LoggerFactory.getLogger(SimpleEmbeddedConfigServerAsymmetricDemoApplication.class);

    @Value("${foo.bar}")
    private String fooBar;

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
//                .environment(new StandardEncryptableServletEnvironment())
                .sources(SimpleEmbeddedConfigServerAsymmetricDemoApplication.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Environment environment = appCtx.getBean(Environment.class);
        LOG.info("Asymmetric encryption");
        LOG.info("Environment's secret: {}", environment.getProperty("secret.property"));
        LOG.info("Environment's secret2: {}", environment.getProperty("secret2.property"));
        LOG.info("Environment's FOO_BAR: {}", environment.getProperty("FOO_BAR"));
        LOG.info("Environment's FOO_BAR from @value: {}", fooBar);
        LOG.info("Done!");
    }
}
