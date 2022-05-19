package com.aboshady.leave.model;

import java.util.List;

public class EmployeeLeavesResponse {
    public EmployeeLeavesResponse(Long employeeId, String employeeName, List<Leave> leaves) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaves = leaves;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<Leave> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Leave> leaves) {
        this.leaves = leaves;
    }

    private Long employeeId;
    private String employeeName;
    private List<Leave> leaves;
}
