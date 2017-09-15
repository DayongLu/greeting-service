package com.dlu.llc.greetingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@EnableDiscoveryClient
@SpringBootApplication
public class GreetingServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(GreetingServiceApplication.class, args);
    }
}


@RestController
class GreetingRestController {


    @RequestMapping(method = RequestMethod.GET, value = "/greeting/{name}")
    Map<String, String> greeting(
      @PathVariable("name")
        String name,
      @RequestHeader("x-forwarded-host")
        Optional<String> host,
      @RequestHeader("x-forwarded-port")
        Optional<Integer> port) {

        host.ifPresent(x -> System.out.println("host = " + x));
        port.ifPresent(x -> System.out.println("port = " + x));

        return Collections.singletonMap("greeting", "Hello, " + name);

    }
}
