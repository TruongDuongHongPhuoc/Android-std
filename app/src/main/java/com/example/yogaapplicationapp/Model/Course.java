package com.example.yogaapplicationapp.Model;

import java.sql.Blob;

public class Course {
        int id;
        String name;
        String startTime; // hours: int
        int duration;
        int capability;
        float pricePerClass;
        String description;
        byte[] image;
        int typeID;
        String daysOfWeek;

        public Course(String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek) {
                this.name = name;
                this.startTime = startTime;
                this.duration = duration;
                this.capability = capability;
                this.pricePerClass = pricePerClass;
                this.description = description;
                this.image = image;
                this.typeID = typeID;
                this.daysOfWeek = daysOfWeek;
        }

        public Course(int id, String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek) {
                this.id = id;
                this.name = name;
                this.startTime = startTime;
                this.duration = duration;
                this.capability = capability;
                this.pricePerClass = pricePerClass;
                this.description = description;
                this.image = image;
                this.typeID = typeID;
                this.daysOfWeek = daysOfWeek;
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

        public String getStartTime() {
                return startTime;
        }

        public void setStartTime(String startTime) {
                this.startTime = startTime;
        }

        public int getDuration() {
                return duration;
        }

        public void setDuration(int duration) {
                this.duration = duration;
        }

        public int getCapability() {
                return capability;
        }

        public void setCapability(int capability) {
                this.capability = capability;
        }

        public float getPricePerClass() {
                return pricePerClass;
        }

        public void setPricePerClass(float pricePerClass) {
                this.pricePerClass = pricePerClass;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public byte[] getImage() {
                return image;
        }

        public void setImage(byte[] image) {
                this.image = image;
        }

        public int getTypeID() {
                return typeID;
        }

        public void setTypeID(int typeID) {
                this.typeID = typeID;
        }

        public String getDaysOfWeek() {
                return daysOfWeek;
        }

        public void setDaysOfWeek(String daysOfWeek) {
                this.daysOfWeek = daysOfWeek;
        }
}
