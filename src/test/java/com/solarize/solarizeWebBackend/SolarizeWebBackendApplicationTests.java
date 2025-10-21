package com.solarize.solarizeWebBackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.mail.username=test@email.com"
})
class SolarizeWebBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
