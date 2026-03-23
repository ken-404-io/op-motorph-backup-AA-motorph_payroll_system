package com.motorph.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

// Encapsulation
public class AttendanceRecord {
    private int employeeId;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public AttendanceRecord(int employeeId, LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        if (timeOut != null && timeIn != null && timeOut.isBefore(timeIn)) {
            throw new IllegalArgumentException("Time out cannot be before time in");
        }
    }

    public Duration getDuration() {
        return Duration.between(timeIn, timeOut);
    }

    public boolean isLate() {
        return timeIn.isAfter(LocalTime.of(8, 10));
    }

    public double getTotalHours() {
        return getDuration().toMinutes() / 60.0;
    }

    public double getRegularHours() {
        double totalHours = getTotalHours();
        return Math.min(totalHours, 8.0);
    }

    public double getOvertimeHours() {
        double totalHours = getTotalHours();
        return totalHours > 8.0 ? totalHours - 8.0 : 0.0;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }
    public LocalTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }
}
