package com.example.orderservice.testutil;

import com.example.orderservice.global.config.JpaConfig;
import com.example.orderservice.global.config.QuerydslConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import({QuerydslConfig.class, JpaConfig.class})
@Transactional
public class JpaTest {

}
