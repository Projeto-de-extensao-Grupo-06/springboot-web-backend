package com.solarize.solarizeWebBackend.shared.cacheManager.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@Configuration
@Profile("prod")
public class Templates {
    final private Environment env;

    public Templates(Environment env) {
        this.env = env;
    }

    private LettuceConnectionFactory loadFactory(int db) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
                Objects.requireNonNull(env.getProperty("spring.data.redis.host")),
                Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.data.redis.port")))
        );
        config.setDatabase(db);


        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    public RedisTemplate<String, String> otpTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(loadFactory(0));
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, String> recoveryTokenTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(loadFactory(1));
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, Boolean> recoveryAttemptTemplate() {
        RedisTemplate<String, Boolean> template = new RedisTemplate<>();
        template.setConnectionFactory(loadFactory(2));
        template.afterPropertiesSet();

        return template;
    }
}
