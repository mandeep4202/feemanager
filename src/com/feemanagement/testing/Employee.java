/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.testing;

/**
 *
 * @author 786
 */
public class Employee {

    private int empNo;
    private String name;
    private int salary;
    private float commission;
    private String friend;
    private Extra ex;

    public Employee(int empNo, String name, int salary, float commission, String friend, Extra ex) {
        this.empNo = empNo;
        this.name = name;
        this.salary = salary;
        this.commission = commission;
        this.friend = friend;
    }

    public Employee() {

    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public Extra getEx() {
        return ex;
    }

    public void setEx(Extra ex) {
        this.ex = ex;
    }

    @Override
    public String toString() {
        System.out.println("called");
        return ex.toString();
    }

}
