package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

public class Bill implements Payable {
    private Appointment appointment;
    private double baseAmount;
    private double tax;
    private double total;

    // Constructor
    public Bill(Appointment appointment, double baseAmount, double tax, double total) {
        this.appointment = appointment;
        this.baseAmount = baseAmount;
        this.tax = tax;
        this.total = total;
    }

    // Constructor with base amount only (calculates tax and total)
    public Bill(Appointment appointment, double baseAmount) {
        this.appointment = appointment;
        this.baseAmount = baseAmount;
        this.tax = calculateTax(baseAmount);
        this.total = baseAmount + this.tax;
    }

    /**
     * Calculate tax based on base amount
     * @param baseAmount the base amount to calculate tax on
     * @return calculated tax amount
     */
    public double calculateTax(double baseAmount) {
        return baseAmount * Constants.TAX_RATE;
    }

    /**
     * Generate BillSummary from this Bill
     * @return BillSummary object with bill details
     */
    public BillSummary generateSummary() {
        return new BillSummary(
            appointment.getId(),
            appointment.getPatient().getName(),
            appointment.getDoctor().getName(),
            this.baseAmount,
            this.tax,
            this.total
        );
    }

    @Override
    public Bill calculateBill() {
        // Recalculate tax and total if needed
        this.tax = calculateTax(this.baseAmount);
        this.total = this.baseAmount + this.tax;
        return this;
    }

    // Getters
    public Appointment getAppointment() {
        return appointment;
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

    // Setters
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
