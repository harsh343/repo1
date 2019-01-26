package com.example.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunAppTests {

@Autowired
ApplicationContext ctx;



@Test
public void testDemo1() {
SpringApplication app = new SpringApplication();
app.run(Demo1Application.class,"input.txt", "3");
}


}