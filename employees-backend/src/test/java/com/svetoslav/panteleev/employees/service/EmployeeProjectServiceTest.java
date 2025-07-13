package com.svetoslav.panteleev.employees.service;

import com.svetoslav.panteleev.employees.model.EmployeeProjectAssignment;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeProjectServiceTest {

	private final EmployeeProjectService employeeProjectService = new EmployeeProjectService();

	@Test
	void testFindLongestWorkingPair_MultipleProjectsBeatSingleLongProject() {
		List<EmployeeProjectAssignment> assignments = List.of(
			new EmployeeProjectAssignment(143, 12, LocalDate.of(2013, 11, 1), LocalDate.of(2014, 1, 5)),
			new EmployeeProjectAssignment(218, 12, LocalDate.of(2013, 12, 1), LocalDate.of(2014, 1, 1)),
			new EmployeeProjectAssignment(143, 10, LocalDate.of(2009, 1, 1), LocalDate.of(2011, 4, 27)),
			new EmployeeProjectAssignment(218, 10, LocalDate.of(2010, 1, 1), LocalDate.of(2011, 1, 1)),
			new EmployeeProjectAssignment(111, 13, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 3, 1)),
			new EmployeeProjectAssignment(222, 13, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 2, 15))
		);

		var result = employeeProjectService.findLongestWorkingPair(assignments);

		assertEquals("143, 218, 398", result);
	}

	@Test
	void testFindLongestWorkingPair_SingleProjectBeatsMultipleShorterProjects() {
		List<EmployeeProjectAssignment> assignments = List.of(
				new EmployeeProjectAssignment(143, 12, LocalDate.of(2013, 11, 1), LocalDate.of(2014, 1, 5)),
				new EmployeeProjectAssignment(218, 12, LocalDate.of(2013, 12, 1), LocalDate.of(2014, 1, 1)),
				new EmployeeProjectAssignment(143, 10, LocalDate.of(2009, 1, 1), LocalDate.of(2011, 4, 27)),
				new EmployeeProjectAssignment(218, 10, LocalDate.of(2010, 1, 1), LocalDate.of(2011, 1, 1)),
				new EmployeeProjectAssignment(111, 13, LocalDate.of(2020, 1, 1), LocalDate.of(2021, 3, 1)),
				new EmployeeProjectAssignment(222, 13, LocalDate.of(2020, 1, 15), LocalDate.of(2022, 2, 15))
		);

		var result = employeeProjectService.findLongestWorkingPair(assignments);

		assertEquals("111, 222, 412", result);
	}

	@Test
	void testFindLongestWorkingPair_NoOverlap_ReturnsNoPairsFound() {
		List<EmployeeProjectAssignment> assignments = List.of(
			new EmployeeProjectAssignment(143, 12, LocalDate.of(2013, 11, 1), LocalDate.of(2014, 1, 5)),
			new EmployeeProjectAssignment(218, 12, LocalDate.of(2014, 2, 1), LocalDate.of(2014, 3, 1))
		);

		var result = employeeProjectService.findLongestWorkingPair(assignments);

		assertEquals("No pairs found", result);
	}

	@Test
	void testProcessCsvFile_emptyFile_throwsIllegalArgumentException() {
		MultipartFile emptyFile = mock(MultipartFile.class);
		when(emptyFile.isEmpty()).thenReturn(true);

		var exception = assertThrows(IllegalArgumentException.class, () -> employeeProjectService.processCsvFile(emptyFile));

		assertEquals("File is empty", exception.getMessage());
	}

	@Test
	void testProcessCsvFile_validFile_returnsResult() {
		String csvContent = "143, 12, 2013-11-01, 2014-01-05\n" +
			"218, 10, 2012-05-16, NULL\n" +
			"143, 10, 2009-01-01, 2011-04-27\n";
		MultipartFile multipartFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

		var result = employeeProjectService.processCsvFile(multipartFile);

		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	void testProcessCsvFile_ioException_throwsRuntimeException() throws Exception {
		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.isEmpty()).thenReturn(false);
		when(mockFile.getInputStream()).thenThrow(new IOException("IO error"));

		var exception = assertThrows(RuntimeException.class, () -> employeeProjectService.processCsvFile(mockFile));

		assertTrue(exception.getMessage().contains("Failed to parse CSV file"));
	}
}