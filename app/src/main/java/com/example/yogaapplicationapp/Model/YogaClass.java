package com.example.yogaapplicationapp.Model;

public class YogaClass {
    int id;
    String name;
    String teacher;
    String comment;
    byte[] image;
    String date;
    int currentCapability;
    int courseID;
    public YogaClass(){

    }
    public YogaClass(int id, String name, String teacher, String comment, byte[] image, String date, int currentCapability, int courseID) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.comment = comment;
        this.image = image;
        this.date = date;
        this.currentCapability = currentCapability;
        this.courseID = courseID;
    }

    public YogaClass(String name, String teacher, String comment, byte[] image, String date, int currentCapability, int courseID) {
        this.name = name;
        this.teacher = teacher;
        this.comment = comment;
        this.image = image;
        this.date = date;
        this.currentCapability = currentCapability;
        this.courseID = courseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrentCapability() {
        return currentCapability;
    }

    public void setCurrentCapability(int currentCapability) {
        this.currentCapability = currentCapability;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
