package com.example.Employees;

public class WorkedTogether {
    private int firstEmpId;
    private int secondEmpId;
    private int projectId;
    private long days;

    public int getFirstEmpId() {
        return firstEmpId;
    }

    public WorkedTogether setFirstEmpId(int firstEmpId) {
        this.firstEmpId = firstEmpId;
        return this;
    }

    public int getSecondEmpId() {
        return secondEmpId;
    }

    public WorkedTogether setSecondEmpId(int secondEmpId) {
        this.secondEmpId = secondEmpId;
        return this;
    }

    public long getDays() {
        return days;
    }

    public int getProjectId() {
        return projectId;
    }

    public WorkedTogether setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public WorkedTogether setDays(long days) {
        this.days = days;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", firstEmpId, secondEmpId, days);
    }
}
