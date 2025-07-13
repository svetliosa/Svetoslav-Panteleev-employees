package com.svetoslav.panteleev.employees.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateParserTest {

	@Test
	void testParseValidIsoFormat() {
		var date = DateParser.parse("2024-07-11");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseBasicIsoFormat() {
		var date = DateParser.parse("20240711");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseNullStringReturnsToday() {
		var today = LocalDate.now();
		var result = DateParser.parse("NULL");
		assertEquals(today, result);
	}

	@Test
	void testParseNullValueReturnsToday() {
		var today = LocalDate.now();
		var result = DateParser.parse(null);
		assertEquals(today, result);
	}

	@Test
	void testParseWithSlashFormat_ddMMyyyy() {
		var date = DateParser.parse("11/07/2024");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseWithSlashFormat_MMddyyyy() {
		var date = DateParser.parse("07/11/2024");
		assertEquals(LocalDate.of(2024, 11, 7), date);
	}

	@Test
	void testParseWithDashFormat_ddMMyyyy() {
		var date = DateParser.parse("11-07-2024");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseWithSlashIsoFormat() {
		var date = DateParser.parse("2024/07/11");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseWithMonthNameFormat_ddMMMyyyy() {
		var date = DateParser.parse("11 Jul 2024");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseWithMonthNameFormat_MMMddyyyy() {
		var date = DateParser.parse("Jul 11 2024");
		assertEquals(LocalDate.of(2024, 7, 11), date);
	}

	@Test
	void testParseInvalidFormatThrowsException() {
		var exception = assertThrows(IllegalArgumentException.class, () -> DateParser.parse("InvalidDate"));
		assertTrue(exception.getMessage().contains("Unsupported date format"));
	}
}