package com.airtribe.meditrack.entity;

public class Patient extends Person implements Cloneable {
    private String symptoms;

    static {
        System.out.println("Patient class loaded");
    }

    // Constructor
    public Patient(String id, String name, int age, String symptoms) {
        super(id, name, age);
        this.symptoms = symptoms;
    }

    // Getters
    public String getSymptoms() {
        return symptoms;
    }

    // Setters
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    // Deep clone implementation
    @Override
    public Patient clone() {
        // Deep clone: create new Patient with copied values
        return new Patient(this.getId(), this.getName(), this.getAge(), this.symptoms);
    }
}
