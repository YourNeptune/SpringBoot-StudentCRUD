package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
	
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args -> {
			Student anna = new Student("Anna","anna@gmail.com",LocalDate.of(2000, Month.JANUARY, 10));
			Student eve = new Student("Eve","eve@gmail.com",LocalDate.of(1989, Month.MAY, 25));
			repository.saveAll(List.of(anna,eve));
		};
	}

}
