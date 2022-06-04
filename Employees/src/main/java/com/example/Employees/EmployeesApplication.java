package com.example.Employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EmployeesApplication.class, args);
		EmployeeService es = new EmployeeService();
	}

}
