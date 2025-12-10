package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class AppointmentService {
    private DataStore<Appointment> appointmentStore;

    public AppointmentService() {
        this.appointmentStore = new DataStore<>();
    }

    public Appointment createAppointment(Doctor doctor, Patient patient, LocalDate date) {
        String id = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(id, doctor, patient, date, AppointmentStatus.SCHEDULED);
        appointmentStore.add(appointment);
        return appointment;
    }

    public void cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.findById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment with ID " + appointmentId + " not found");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    public List<Appointment> listAppointments() {
        return appointmentStore.findAll();
    }
}
