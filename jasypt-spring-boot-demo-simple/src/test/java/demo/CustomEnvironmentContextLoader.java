package demo;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

public class CustomEnvironmentContextLoader extends SpringBootContextLoader {
    @Override
    protected ConfigurableEnvironment getEnvironment() {
        return StandardEncryptableEnvironment
                .builder()
                .skipPropertySourceClasses((List) List.of(RandomValuePropertySource.class))
                .build();
    }
}
