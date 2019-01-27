package com.example.demo1;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RunAppTests {

    @Autowired
    ApplicationContext ctx;


    @Test
    public void testDemo1() throws Exception {
        SpringApplication app = new SpringApplication();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());
        System.out.println(file.getAbsolutePath());
        app.run(RunApp.class, file.getAbsolutePath(), "3");

        File expectedOutputFile = new File(classLoader.getResource("expectedOutput.json").getFile());
        File actualOutputFile = new File("target/output.json");

        Assert.assertTrue("The files differ!",
                FileUtils.contentEquals(expectedOutputFile, actualOutputFile));

    }


}