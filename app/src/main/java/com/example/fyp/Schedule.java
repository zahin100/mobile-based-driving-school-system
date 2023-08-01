package com.example.fyp;

import java.util.ArrayList;

public class Schedule {

    private int studentID;

    private ArrayList<Integer> studentIDList = new ArrayList<>();
    private ArrayList<Integer> scheduleIDList = new ArrayList<>();
    private ArrayList<Integer> staffIDList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();

    private ArrayList<String> nameList = new ArrayList<>();

    private ArrayList<String> licenceTypeList = new ArrayList<>();

    private ArrayList<Integer> progressList = new ArrayList<>();
    private boolean isScheduleEmpty;

    private static final Schedule instance = new Schedule();

    public static Schedule getInstance(){
        return instance;

    }

    public Schedule(){
        super();
    }



    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public ArrayList<Integer> getScheduleIDList() {
        return scheduleIDList;
    }

    public void setScheduleIDList(ArrayList<Integer> scheduleIDList) {
        this.scheduleIDList = scheduleIDList;
    }

    public ArrayList<Integer> getStaffIDList() {
        return staffIDList;
    }

    public void setStaffIDList(ArrayList<Integer> staffIDList) {
        this.staffIDList = staffIDList;
    }

    public ArrayList<String> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<String> dateList) {
        this.dateList = dateList;
    }

    public ArrayList<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<String> statusList) {
        this.statusList = statusList;
    }

    public boolean isScheduleEmpty() {
        return isScheduleEmpty;
    }

    public void setScheduleEmpty(boolean scheduleEmpty) {
        isScheduleEmpty = scheduleEmpty;
    }

    public ArrayList<Integer> getStudentIDList() {
        return studentIDList;
    }

    public void setStudentIDList(ArrayList<Integer> studentIDList) {
        this.studentIDList = studentIDList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public ArrayList<String> getLicenceTypeList() {
        return licenceTypeList;
    }

    public void setLicenceTypeList(ArrayList<String> licenceTypeList) {
        this.licenceTypeList = licenceTypeList;
    }

    public ArrayList<Integer> getProgressList() {
        return progressList;
    }

    public void setProgressList(ArrayList<Integer> progressList) {
        this.progressList = progressList;
    }
}
