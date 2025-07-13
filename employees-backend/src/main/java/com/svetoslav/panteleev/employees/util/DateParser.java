package com.svetoslav.panteleev.employees.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class DateParser {
	private static final List<DateTimeFormatter> FORMATTERS = List.of(
		DateTimeFormatter.ISO_LOCAL_DATE,
		DateTimeFormatter.BASIC_ISO_DATE,
		DateTimeFormatter.ofPattern("dd-MM-yyyy"),
		DateTimeFormatter.ofPattern("dd/MM/yyyy"),
		DateTimeFormatter.ofPattern("MM/dd/yyyy"),
		DateTimeFormatter.ofPattern("yyyy/MM/dd"),
		DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH),
		DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH)
	);

	public static LocalDate parse(String dateStr) {
		if (dateStr == null || dateStr.equalsIgnoreCase("NULL"))
			return LocalDate.now();

		for (DateTimeFormatter formatter : FORMATTERS) {
			try {
				return LocalDate.parse(dateStr, formatter);
			} catch (DateTimeParseException e) {
				// Try next format
			}
		}
		throw new IllegalArgumentException(String.format("Unsupported date format: \"%s\"", dateStr));
	}
}
