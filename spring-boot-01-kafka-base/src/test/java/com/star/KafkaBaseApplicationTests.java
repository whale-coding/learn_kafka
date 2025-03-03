package com.star;

import com.star.producer.EventProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaBaseApplicationTests {

	@Resource
	private EventProducer eventProducer;

	@Test
	void test01() {
		eventProducer.sendEvent();
	}

	@Test
	void test02() {
		eventProducer.sendEvent2();
	}

	@Test
	void test03() {
		eventProducer.sendEvent3();
	}

	@Test
	void test04() {
		eventProducer.sendEvent4();
	}

	@Test
	void test05() {
		eventProducer.sendEvent5();
	}

}
