package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private DataStore<Doctor> doctorStore;

    public DoctorService() {
        this.doctorStore = new DataStore<>();
    }

    public Doctor addDoctor(String name, int age, Specialization specialization, double fee) {
        String id = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(id, name, age, specialization, fee);
        doctorStore.add(doctor);
        return doctor;
    }

    public List<Doctor> searchDoctor(String name) {
        return doctorStore.findByName(name);
    }

    public List<Doctor> listDoctors() {
        return doctorStore.findAll();
    }

    /**
     * Filter doctors by specialization using streams
     * @param specialization the specialization to filter by
     * @return list of doctors with the specified specialization
     */
    public List<Doctor> filterBySpecialization(Specialization specialization) {
        return doctorStore.findAll().stream()
                .filter(doctor -> doctor.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    /**
     * Calculate average consultation fee using streams
     * @return average fee of all doctors, or 0.0 if no doctors exist
     */
    public double averageFee() {
        return doctorStore.findAll().stream()
                .mapToDouble(Doctor::getFee)
                .average()
                .orElse(0.0);
    }
}
