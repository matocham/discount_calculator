package com.challenge.discountcalculator;

import org.springframework.boot.SpringApplication;

public class TestDiscountCalculatorApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
        SpringApplication.from(DiscountCalculatorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
