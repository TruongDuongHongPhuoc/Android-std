package com.example.yogaapplicationapp.Model;

public class Type {
    int id;
    String typeName;

    public Type(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Type(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
