package demo.config;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Adds a property 'resources-path' that resolves to the root of the classpath for reuse
 * in application.yml (e.g. building the H2 file URL).
 */
@Configuration
public class ResourcesPathConfig {

    @Bean
    public static BeanDefinitionRegistryPostProcessor resourcesPathPropertySourcePostProcessor(ConfigurableEnvironment env) {
        return new BeanDefinitionRegistryPostProcessor() {
            @SneakyThrows
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                Map<String, Object> map = new HashMap<>();
                map.put("resources-path", new ClassPathResource("./").getURL());
                PropertySource<?> ps = new MapPropertySource("resources-path", map);
                env.getPropertySources().addLast(ps);
            }
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException { }
        };}
}

