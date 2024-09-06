package com.example.showservice.testutil;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.transaction.annotation.Transactional;

@EmbeddedKafka
@Transactional
@SpringBootTest
public class IntegrationTest {
}
