package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static AtomicLong patientCounter = new AtomicLong(1);
    private static AtomicLong doctorCounter = new AtomicLong(1);
    private static AtomicLong appointmentCounter = new AtomicLong(1);

    public static String generatePatientId() {
        return "PAT" + String.format("%04d", patientCounter.getAndIncrement());
    }

    public static String generateDoctorId() {
        return "DOC" + String.format("%04d", doctorCounter.getAndIncrement());
    }

    public static String generateAppointmentId() {
        return "APT" + String.format("%04d", appointmentCounter.getAndIncrement());
    }
}
