package com.svetoslav.panteleev.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectAssignment {
	private int empId;
	private int projectId;
	private LocalDate dateFrom;
	private LocalDate dateTo;
}
