package com.solarize.solarizeWebBackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"EMAIL=test@example.com"})
class SolarizeWebBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
