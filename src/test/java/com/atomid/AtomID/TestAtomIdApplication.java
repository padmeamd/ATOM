package com.atomid.AtomID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAtomIdApplication {

	public static void main(String[] args) {
		SpringApplication.from(AtomIdApplication::main).with(TestAtomIdApplication.class).run(args);
	}

}
