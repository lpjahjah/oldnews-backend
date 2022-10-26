package com.oldnews.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OldnewsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldnewsBackendApplication.class, args);

        System.out.println(
                "\n       SERVER ONLINE\n"
        );
    }

}
