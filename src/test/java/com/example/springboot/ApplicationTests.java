package com.example.springboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static final GenericContainer<?> myapp=new GenericContainer<>(
            DockerImageName.parse("devapp")
    ).withExposedPorts(8080);

    @Container
    public static final GenericContainer<?> myapp2=new GenericContainer<>(
            DockerImageName.parse("prodapp")
    ).withExposedPorts(8081);

    @Test
    void Test1() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+myapp.getMappedPort(8080)+"/profile", String.class);
        System.out.println("TEST 1: "+response.getBody());
        Assertions.assertEquals("Current profile is dev", response.getBody());
    }
    @Test
    void Test2(){
        ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:"+myapp2.getMappedPort(8081)+"/profile",String.class);
        System.out.println("TEST 2: "+response.getBody());
        Assertions.assertEquals("Current profile is production",response.getBody());
    }
}
