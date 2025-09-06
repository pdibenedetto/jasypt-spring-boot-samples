package bootstrap;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import demo.config.ResourcesPathConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Bootstrap application responsible for creating the H2 database file, the user ULI, and seed data.
 * Activated with the 'bootstrap' profile. Exits after seeding when configured.
 */
@SpringBootApplication(scanBasePackages = {"bootstrap"})
@EnableEncryptableProperties
@EnableJpaRepositories(basePackages = "demo")
@EntityScan(basePackages = "demo")
@Profile("bootstrap")
@Import(ResourcesPathConfig.class)
public class BootstrapApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BootstrapApplication.class)
                .profiles("bootstrap")
                .run(args);
    }
}
