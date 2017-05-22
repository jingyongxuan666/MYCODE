package com.hzr.cloudstation.entities;

import java.io.Serializable;

/**
 * Created by hzr on 2017/4/16.
 */
public class Employee implements Serializable {
    private String empId;
    private String empName;
    private String tel;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Employee(String empId, String empName, String tel) {
        this.empId = empId;
        this.empName = empName;
        this.tel = tel;
    }

    public Employee() {
        super();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
