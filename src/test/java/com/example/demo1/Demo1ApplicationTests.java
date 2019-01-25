package com.example.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

	@Test
	public void testDemo1() {
		SpringApplication app = new SpringApplication();
		app.run(new String[] {"input.txt", "3"});
	}

}

