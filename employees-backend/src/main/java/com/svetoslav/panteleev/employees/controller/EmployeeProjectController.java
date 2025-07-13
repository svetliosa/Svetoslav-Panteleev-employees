package com.svetoslav.panteleev.employees.controller;

import com.svetoslav.panteleev.employees.dto.ProjectCollaborationDto;
import com.svetoslav.panteleev.employees.service.EmployeeProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProjectController {

	private final EmployeeProjectService employeeProjectService;

	public EmployeeProjectController(EmployeeProjectService employeeProjectService) {
		this.employeeProjectService = employeeProjectService;
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		return employeeProjectService.processCsvFile(file);
	}

	@PostMapping("/load-csv-data")
	public List<ProjectCollaborationDto> loadCsvData(@RequestParam("file") MultipartFile file) {
		return employeeProjectService.getAllCollaborationsFromFile(file);
	}
}
