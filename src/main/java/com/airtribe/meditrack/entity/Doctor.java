package com.airtribe.meditrack.entity;

public class Doctor extends Person {
    private Specialization specialization;
    private double fee;

    // Constructor
    public Doctor(String id, String name, int age, Specialization specialization, double fee) {
        super(id, name, age);
        this.specialization = specialization;
        this.fee = fee;
    }

    // Getters
    public Specialization getSpecialization() {
        return specialization;
    }

    public double getFee() {
        return fee;
    }

    // Setters
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
