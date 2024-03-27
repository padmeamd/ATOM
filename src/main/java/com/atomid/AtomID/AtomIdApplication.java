package com.atomid.AtomID;

import com.atomid.AtomID.entity.Message;
import com.atomid.AtomID.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class AtomIdApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(AtomIdApplication.class, args);
		var messageRepository = context.getBean(MessageRepository.class);
	}

}
