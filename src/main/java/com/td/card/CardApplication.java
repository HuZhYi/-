package com.td.card;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "com.td.card.dao")
@SpringBootApplication
public class CardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }

}

