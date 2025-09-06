package demo;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import bootstrap.BootstrapApplication;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Sample Boot application that showcases easy integration of Jasypt encryption by
 * simply adding {@literal @EnableEncryptableProperties} to any Configuration class.
 * For decryption a password is required and is set through system properties in this example,
 * but it could be passed command line argument too like this: --jasypt.encryptor.password=password
 */
@SpringBootApplication
@EnableEncryptableProperties
public class DBH2DemoApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DBH2DemoApplication.class);

    static {
        System.setProperty("jasypt.encryptor.password", "password");
    }

    @Autowired
    ApplicationContext appCtx;

    public static void main(String[] args) {
        // Run bootstrap application first (creates DB, user, data)
        new SpringApplicationBuilder(BootstrapApplication.class)
                .profiles("bootstrap")
                .run()
                .close();

        // Then run main application (connects as ULI with encrypted password)
        new SpringApplicationBuilder(DBH2DemoApplication.class)
                .run(args);
    }

    @Override
    @SneakyThrows
    public void run(String... args) {
        LOG.info("**********************************************************");
        LOG.info("DB User: {}", appCtx.getEnvironment().getProperty("spring.datasource.username"));
        LOG.info("DB pass (decrypted): {}", appCtx.getEnvironment().getProperty("spring.datasource.password"));
        PersonRepository repo = appCtx.getBean(PersonRepository.class);
        LOG.info("People with last name Bocchio: {}", repo.findByLastName("Bocchio"));
        LOG.info("**********************************************************");
    }
}
