package com.example.Employees;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static java.util.Comparator.comparing;

public class EmployeeService {
    private List<Employee> employees = new ArrayList<Employee>();

    public EmployeeService() throws IOException {
        try {
            File file = new File("D:/Employees/employees.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();

            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                employees.add(new Employee()
                        .setEmpId(Integer.parseInt(cellIterator.next().getStringCellValue()))
                        .setProjectId(Integer.parseInt(cellIterator.next().getStringCellValue()))
                        .setDateFrom(LocalDate.parse(cellIterator.next().getStringCellValue()))
                        .setDateTo(cellIterator.next().getStringCellValue())
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        CalculateTimeSpan();
    }

    // Calculates the biggest timespan of two employees working on a same project
    public void CalculateTimeSpan()
    {
        var timespan = 0L;
        var list = new ArrayList<WorkedTogether>();
        for (int i = 1; i < employees.size(); i++)
        {
            if(employees.get(i-1).getProjectId() == employees.get(i).getProjectId()) {
                timespan = CalculateRange(employees.get(i - 1).getDateFrom(), employees.get(i - 1).getDateTo(), employees.get(i).getDateFrom(), employees.get(i).getDateTo());
                if (timespan != 0)
                    list.add(new WorkedTogether()
                            .setFirstEmpId(employees.get(i - 1).getEmpId())
                            .setSecondEmpId(employees.get(i).getEmpId())
                            .setDays(timespan)
                    );
            }
        }
        Collections.sort(list, comparing(WorkedTogether::getDays));
        if (list.size() != 0)
            System.out.println(list.get(0).toString());
    }

    // Calculates the timespan
    public long CalculateRange (LocalDate DateFrom1,LocalDate DateTo1,LocalDate DateFrom2,LocalDate DateTo2)
    {
        var timespan = 0L;
        if( DateFrom1.isBefore(DateTo1) && DateFrom2.isBefore(DateTo2)) {
            if (DateFrom1.isBefore(DateFrom2) && DateTo1.isBefore(DateTo2) && DateFrom2.isBefore(DateTo1)) {
                timespan = ChronoUnit.DAYS.between(DateFrom2, DateTo1);
            } else if (DateFrom1.isBefore(DateFrom2) && DateTo1.isAfter(DateTo2)) {
                timespan = ChronoUnit.DAYS.between(DateFrom2, DateTo2);
            } else if (DateFrom1.isAfter(DateFrom2) && DateTo1.isBefore(DateTo2)) {
                timespan = ChronoUnit.DAYS.between(DateFrom1, DateTo1);
            } else if (DateFrom1.isAfter(DateFrom2) && DateTo1.isAfter(DateTo2) && DateFrom1.isBefore(DateTo2)) {
                timespan = ChronoUnit.DAYS.between(DateFrom1, DateTo2);
            }
        }
        return timespan;
    }
}




