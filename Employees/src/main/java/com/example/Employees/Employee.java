package com.example.Employees;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static java.time.LocalDate.now;

public class Employee {
    public int empId;
    public int projectId;
    public LocalDate dateFrom;
    public LocalDate dateTo;

    public int getEmpId() {
        return empId;
    }

    public Employee setEmpId(int empId) {
        this.empId = empId;
        return this;
    }

    public int getProjectId() {
        return projectId;
    }

    public Employee setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public Employee setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Employee setDateTo(String dateTo) {
        this.dateTo = dateTo.equals("NULL") ? now() : LocalDate.parse(dateTo);
        return this;
    }

    @Override
    public String toString() {
        return String.format("EmpId: %s, ProjectId: %s, DateFrom: %s, DateTo: %s", empId, projectId, dateFrom, dateTo);
    }
}
