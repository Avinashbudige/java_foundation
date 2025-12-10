package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.List;

public class PatientService {
    private DataStore<Patient> patientStore;

    public PatientService() {
        this.patientStore = new DataStore<>();
    }

    public Patient addPatient(String name, int age, String symptoms) {
        String id = IdGenerator.generatePatientId();
        Patient patient = new Patient(id, name, age, symptoms);
        patientStore.add(patient);
        return patient;
    }

    public Patient getPatientById(String id) {
        return patientStore.findById(id);
    }

    public List<Patient> searchPatient(String name) {
        return patientStore.findByName(name);
    }

    public List<Patient> listPatients() {
        return patientStore.findAll();
    }
}
