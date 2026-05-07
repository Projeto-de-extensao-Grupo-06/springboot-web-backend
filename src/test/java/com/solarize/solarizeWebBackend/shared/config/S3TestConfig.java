package com.solarize.solarizeWebBackend.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.s3.S3Client;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test")
public class S3TestConfig {
    
    @Bean
    public S3Client s3Client() {
        return mock(S3Client.class);
    }
}
