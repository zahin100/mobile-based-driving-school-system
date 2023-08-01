package com.example.fyp;

import java.util.ArrayList;
import java.util.Date;

public class Student {

    private int studentID,progress;
    private String name, ICNum, address, phoneNum, username, password;
    private boolean isRegister, isStudent, isDataEmpty;
    private Date expiryDate;
    private Licence licence;
    private ArrayList<Integer> studentIDList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> licenceTypeList = new ArrayList<>();

    private ArrayList<Integer> progressList = new ArrayList<>();

    private static final Student instance = new Student();

    public static Student getInstance(){
        return instance;

    }

    public Student(){
        super();
    }

    public Student(String name, String ICNum, String address, String phoneNum, String username, String password, boolean isRegister, Date expiryDate) {
        this.name = name;
        this.ICNum = ICNum;
        this.address = address;
        this.phoneNum = phoneNum;
        this.username = username;
        this.password = password;
        this.isRegister = isRegister;
        this.expiryDate = expiryDate;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }


    public String getICNum() {
        return ICNum;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setICNum(String ICNum) {
        this.ICNum = ICNum;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public void setIsRegister(boolean isRegister) {this.isRegister = isRegister;}

    public boolean getIsRegister() { return isRegister; }

    public void setExpiryDate(Date expiryDate) {this.expiryDate = expiryDate;}

    public Date getExpiryDate() { return expiryDate; }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setIsStudent(boolean student) {
        isStudent = student;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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

    public boolean isDataEmpty() {
        return isDataEmpty;
    }

    public void setDataEmpty(boolean dataEmpty) {
        isDataEmpty = dataEmpty;
    }


}
