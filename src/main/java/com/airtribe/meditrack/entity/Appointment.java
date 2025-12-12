package com.airtribe.meditrack.entity;

import java.time.LocalDate;

public class Appointment implements Cloneable {
    private String id;
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private AppointmentStatus status;

    static {
        System.out.println("Appointment class loaded");
    }

    // Constructor
    public Appointment(String id, Doctor doctor, Patient patient, LocalDate date, AppointmentStatus status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    // Deep clone implementation - clones nested objects
    @Override
    public Appointment clone() {
        // Deep clone: create new Doctor and Patient objects
        Doctor clonedDoctor = new Doctor(
            this.doctor.getId(),
            this.doctor.getName(),
            this.doctor.getAge(),
            this.doctor.getSpecialization(),
            this.doctor.getFee()
        );
        Patient clonedPatient = this.patient.clone();
        
        return new Appointment(
            this.id,
            clonedDoctor,
            clonedPatient,
            this.date,
            this.status
        );
    }
}
