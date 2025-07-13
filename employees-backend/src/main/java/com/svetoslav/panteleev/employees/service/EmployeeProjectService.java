package com.svetoslav.panteleev.employees.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.svetoslav.panteleev.employees.dto.ProjectCollaborationDto;
import com.svetoslav.panteleev.employees.model.EmployeeProjectAssignment;
import com.svetoslav.panteleev.employees.util.DateParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeProjectService {
	public String processCsvFile(MultipartFile file) {
		try {
			if (file.isEmpty())
				throw new IllegalArgumentException("File is empty");

			List<EmployeeProjectAssignment> assignments = parseCsv(file);
			return findLongestWorkingPair(assignments);
		} catch (IOException | CsvException e) {
			throw new RuntimeException("Failed to parse CSV file", e);
		}
	}

	public List<ProjectCollaborationDto> getAllCollaborationsFromFile(MultipartFile file) {
		try {
			if (file.isEmpty())
				throw new IllegalArgumentException("File is empty");

			List<EmployeeProjectAssignment> assignments = parseCsv(file);
			return getAllCollaborations(assignments);
		} catch (IOException | CsvException e) {
			throw new RuntimeException("Failed to parse CSV file", e);
		}
	}

	public List<EmployeeProjectAssignment> parseCsv(MultipartFile file) throws IOException, CsvException {
		try {
			var csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
			var lines = csvReader.readAll();
			if (!lines.isEmpty() && containsHeaderRow(lines.getFirst()))
				lines.removeFirst();

			return lines.stream()
				.map(line -> new EmployeeProjectAssignment(
					Integer.parseInt(line[0].trim()),
					Integer.parseInt(line[1].trim()),
					DateParser.parse(line[2].trim()),
					DateParser.parse(line[3].trim())
				))
				.collect(Collectors.toList());
		} catch (IOException | CsvValidationException e) {
			throw new RuntimeException("Failed to parse CSV file", e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean containsHeaderRow(String[] row) {
		if (row.length < 4) return false;
		return row[0].trim().equalsIgnoreCase("EmpID") &&
			row[1].trim().equalsIgnoreCase("ProjectID") &&
			row[2].trim().equalsIgnoreCase("DateFrom") &&
			row[3].trim().equalsIgnoreCase("DateTo");
	}

	public String findLongestWorkingPair(List<EmployeeProjectAssignment> assignments) {
		Map<Integer, List<EmployeeProjectAssignment>> projectsGrouped = assignments.stream().collect(Collectors.groupingBy(EmployeeProjectAssignment::getProjectId));
		Map<String, Long> pairDurations = projectsGrouped.values().stream()
			.flatMap(projectList ->
				IntStream.range(0, projectList.size())
					.boxed()
					.flatMap(i ->
						IntStream.range(i + 1, projectList.size())
							.mapToObj(j -> {
								var firstEmployee = projectList.get(i);
								var secondEmployee = projectList.get(j);
								var firstEmployeeId = Math.min(firstEmployee.getEmpId(), secondEmployee.getEmpId());
								var secondEmployeeId = Math.max(firstEmployee.getEmpId(), secondEmployee.getEmpId());
								var overlapDays = calculateOverlap(firstEmployee, secondEmployee);
								return new AbstractMap.SimpleEntry<>(firstEmployeeId + "," + secondEmployeeId, overlapDays);
							})
					)
			)
			.filter(entry -> entry.getValue() > 0)
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue,
				/* Since the task states that the employees should have worked on 'common projectS', I’ve implemented the solution to sum the total time worked together
				across all shared projects for each pair of employees. If the requirement was to find only the maximum time spent on a single project, then it should be a 'max' instead. */
				Long::sum
			));

		return pairDurations.entrySet().stream()
			.max(Map.Entry.comparingByValue())
			.map(entry -> {
				var empIds = entry.getKey().split(",");
				return String.format("%s, %s, %d", empIds[0].trim(), empIds[1].trim(), entry.getValue());
			})
			.orElse("No pairs found");
	}

	private long calculateOverlap(EmployeeProjectAssignment e1, EmployeeProjectAssignment e2) {
		var start = Collections.max(List.of(e1.getDateFrom(), e2.getDateFrom()));
		var end = Collections.min(List.of(e1.getDateTo(), e2.getDateTo()));
		var days = ChronoUnit.DAYS.between(start, end) + 1;
		return days > 0 ? days : 0;
	}

	public List<ProjectCollaborationDto> getAllCollaborations(List<EmployeeProjectAssignment> assignments) {
		return assignments.stream()
			.collect(Collectors.groupingBy(EmployeeProjectAssignment::getProjectId))
			.entrySet().stream()
			.flatMap(entry -> {
				var projectId = entry.getKey().longValue();
				List<EmployeeProjectAssignment> projectAssignments = entry.getValue();

				return IntStream.range(0, projectAssignments.size())
					.boxed()
					.flatMap(i -> IntStream.range(i + 1, projectAssignments.size())
						.mapToObj(j -> {
							var firstEmployee = projectAssignments.get(i);
							var secondEmployee = projectAssignments.get(j);
							var overlap = calculateOverlap(firstEmployee, secondEmployee);
							if (overlap <= 0)
								return null;

							var firstEmployeeId = (long) Math.min(firstEmployee.getEmpId(), secondEmployee.getEmpId());
							var secondEmployeeId = (long) Math.max(firstEmployee.getEmpId(), secondEmployee.getEmpId());

							return new ProjectCollaborationDto(firstEmployeeId, secondEmployeeId, projectId, overlap);
						})
					)
					.filter(Objects::nonNull);
			})
			.collect(Collectors.toList());
	}
}
