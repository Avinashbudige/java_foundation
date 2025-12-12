package com.airtribe.meditrack.entity;

// Immutable class
public final class BillSummary {
    private final String appointmentId;
    private final String patientName;
    private final String doctorName;
    private final double baseAmount;
    private final double tax;
    private final double total;

    // Constructor
    public BillSummary(String appointmentId, String patientName, String doctorName, 
                      double baseAmount, double tax, double total) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.baseAmount = baseAmount;
        this.tax = tax;
        this.total = total;
    }

    // Only getters (no setters - immutable)
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }
}
