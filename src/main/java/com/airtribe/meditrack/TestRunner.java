package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.time.LocalDate;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=== MediTrack Test Runner ===\n");
        
        try {
            testPatientService();
            testDoctorService();
            testAppointmentService();
            testBilling();
            testCloning();
            testStreamsAndLambdas();
            
            System.out.println("\n=== All Tests Completed Successfully ===");
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testPatientService() {
        System.out.println("--- Testing PatientService ---");
        PatientService patientService = new PatientService();

        // Test addPatient
        System.out.println("1. Adding patients...");
        Patient patient1 = patientService.addPatient("John Doe", 35, "Fever and cough");
        Patient patient2 = patientService.addPatient("Jane Smith", 28, "Headache");
        Patient patient3 = patientService.addPatient("Bob Johnson", 45, "Back pain");
        System.out.println("   ✓ Added Patient 1: " + patient1.getId() + " - " + patient1.getName());
        System.out.println("   ✓ Added Patient 2: " + patient2.getId() + " - " + patient2.getName());
        System.out.println("   ✓ Added Patient 3: " + patient3.getId() + " - " + patient3.getName());

        // Test getPatientById
        System.out.println("\n2. Getting patient by ID...");
        Patient found = patientService.getPatientById(patient1.getId());
        if (found != null) {
            System.out.println("   ✓ Found patient: " + found.getName() + " (Age: " + found.getAge() + ")");
        }

        // Test searchPatient
        System.out.println("\n3. Searching patients by name...");
        List<Patient> searchResults = patientService.searchPatient("John");
        System.out.println("   ✓ Found " + searchResults.size() + " patient(s) matching 'John'");
        for (Patient p : searchResults) {
            System.out.println("     - " + p.getName() + " (" + p.getId() + ")");
        }

        // Test listPatients
        System.out.println("\n4. Listing all patients...");
        List<Patient> allPatients = patientService.listPatients();
        System.out.println("   ✓ Total patients: " + allPatients.size());
        for (Patient p : allPatients) {
            System.out.println("     - " + p.getName() + " | Symptoms: " + p.getSymptoms());
        }
        System.out.println();
    }

    private static void testDoctorService() {
        System.out.println("--- Testing DoctorService ---");
        DoctorService doctorService = new DoctorService();

        // Test addDoctor
        System.out.println("1. Adding doctors...");
        Doctor doctor1 = doctorService.addDoctor("Dr. Sarah Williams", 40, Specialization.CARDIOLOGY, 1500.0);
        Doctor doctor2 = doctorService.addDoctor("Dr. Michael Brown", 35, Specialization.NEUROLOGY, 2000.0);
        Doctor doctor3 = doctorService.addDoctor("Dr. Emily Davis", 38, Specialization.CARDIOLOGY, 1800.0);
        Doctor doctor4 = doctorService.addDoctor("Dr. James Wilson", 42, Specialization.PEDIATRICS, 1200.0);
        System.out.println("   ✓ Added Doctor 1: " + doctor1.getName() + " (" + doctor1.getSpecialization() + ")");
        System.out.println("   ✓ Added Doctor 2: " + doctor2.getName() + " (" + doctor2.getSpecialization() + ")");
        System.out.println("   ✓ Added Doctor 3: " + doctor3.getName() + " (" + doctor3.getSpecialization() + ")");
        System.out.println("   ✓ Added Doctor 4: " + doctor4.getName() + " (" + doctor4.getSpecialization() + ")");

        // Test searchDoctor
        System.out.println("\n2. Searching doctors by name...");
        List<Doctor> searchResults = doctorService.searchDoctor("Sarah");
        System.out.println("   ✓ Found " + searchResults.size() + " doctor(s) matching 'Sarah'");
        for (Doctor d : searchResults) {
            System.out.println("     - " + d.getName() + " | Fee: $" + d.getFee());
        }

        // Test listDoctors
        System.out.println("\n3. Listing all doctors...");
        List<Doctor> allDoctors = doctorService.listDoctors();
        System.out.println("   ✓ Total doctors: " + allDoctors.size());
        for (Doctor d : allDoctors) {
            System.out.println("     - " + d.getName() + " | " + d.getSpecialization() + " | Fee: $" + d.getFee());
        }
        System.out.println();
    }

    private static void testAppointmentService() {
        System.out.println("--- Testing AppointmentService ---");
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        // Create test data
        Patient patient = patientService.addPatient("Test Patient", 30, "General checkup");
        Doctor doctor = doctorService.addDoctor("Dr. Test Doctor", 40, Specialization.GENERAL, 1000.0);

        // Test createAppointment
        System.out.println("1. Creating appointments...");
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        
        Appointment appointment1 = appointmentService.createAppointment(doctor, patient, today);
        Appointment appointment2 = appointmentService.createAppointment(doctor, patient, tomorrow);
        System.out.println("   ✓ Created Appointment 1: " + appointment1.getId() + " on " + appointment1.getDate());
        System.out.println("   ✓ Created Appointment 2: " + appointment2.getId() + " on " + appointment2.getDate());
        System.out.println("   ✓ Appointment 1 Status: " + appointment1.getStatus());

        // Test listAppointments
        System.out.println("\n2. Listing all appointments...");
        List<Appointment> allAppointments = appointmentService.listAppointments();
        System.out.println("   ✓ Total appointments: " + allAppointments.size());
        for (Appointment apt : allAppointments) {
            System.out.println("     - " + apt.getId() + " | " + apt.getDoctor().getName() + 
                             " -> " + apt.getPatient().getName() + " | " + apt.getDate() + 
                             " | " + apt.getStatus());
        }

        // Test cancelAppointment
        System.out.println("\n3. Cancelling an appointment...");
        try {
            appointmentService.cancelAppointment(appointment1.getId());
            Appointment cancelled = appointmentService.listAppointments().stream()
                    .filter(a -> a.getId().equals(appointment1.getId()))
                    .findFirst()
                    .orElse(null);
            if (cancelled != null && cancelled.getStatus() == AppointmentStatus.CANCELLED) {
                System.out.println("   ✓ Appointment cancelled successfully. Status: " + cancelled.getStatus());
            }
        } catch (Exception e) {
            System.out.println("   ✗ Error cancelling appointment: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testBilling() {
        System.out.println("--- Testing Billing System ---");
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        // Create test data
        Patient patient = patientService.addPatient("Billing Patient", 35, "Consultation");
        Doctor doctor = doctorService.addDoctor("Dr. Billing", 45, Specialization.CARDIOLOGY, 2000.0);
        Appointment appointment = appointmentService.createAppointment(doctor, patient, LocalDate.now());

        // Test Bill creation and calculateTax
        System.out.println("1. Creating bill and calculating tax...");
        double baseAmount = doctor.getFee();
        Bill bill = new Bill(appointment, baseAmount);
        double calculatedTax = bill.calculateTax(baseAmount);
        System.out.println("   ✓ Base Amount: $" + baseAmount);
        System.out.println("   ✓ Calculated Tax (18%): $" + String.format("%.2f", calculatedTax));
        System.out.println("   ✓ Total Amount: $" + String.format("%.2f", bill.getTotal()));

        // Test Payable interface
        System.out.println("\n2. Testing Payable interface...");
        Bill payableBill = bill.calculateBill();
        System.out.println("   ✓ calculateBill() returned: $" + String.format("%.2f", payableBill.getTotal()));

        // Test generateSummary
        System.out.println("\n3. Generating bill summary...");
        BillSummary summary = bill.generateSummary();
        System.out.println("   ✓ Bill Summary:");
        System.out.println("     - Appointment ID: " + summary.getAppointmentId());
        System.out.println("     - Patient: " + summary.getPatientName());
        System.out.println("     - Doctor: " + summary.getDoctorName());
        System.out.println("     - Base Amount: $" + String.format("%.2f", summary.getBaseAmount()));
        System.out.println("     - Tax: $" + String.format("%.2f", summary.getTax()));
        System.out.println("     - Total: $" + String.format("%.2f", summary.getTotal()));
        System.out.println();
    }

    private static void testCloning() {
        System.out.println("--- Testing Cloning Functionality ---");
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        // Create test data
        Patient originalPatient = patientService.addPatient("Clone Patient", 25, "Test symptoms");
        Doctor doctor = doctorService.addDoctor("Dr. Clone", 40, Specialization.GENERAL, 1000.0);
        Appointment originalAppointment = appointmentService.createAppointment(doctor, originalPatient, LocalDate.now());

        // Test Patient cloning
        System.out.println("1. Testing Patient deep clone...");
        Patient clonedPatient = originalPatient.clone();
        System.out.println("   ✓ Original Patient ID: " + originalPatient.getId());
        System.out.println("   ✓ Cloned Patient ID: " + clonedPatient.getId());
        System.out.println("   ✓ Cloned Patient Name: " + clonedPatient.getName());
        System.out.println("   ✓ Cloned Patient Symptoms: " + clonedPatient.getSymptoms());
        
        // Modify clone to verify it's a deep copy
        clonedPatient.setSymptoms("Modified symptoms");
        System.out.println("   ✓ After modifying clone, original symptoms: " + originalPatient.getSymptoms());
        System.out.println("   ✓ Clone symptoms: " + clonedPatient.getSymptoms());

        // Test Appointment cloning
        System.out.println("\n2. Testing Appointment deep clone...");
        Appointment clonedAppointment = originalAppointment.clone();
        System.out.println("   ✓ Original Appointment ID: " + originalAppointment.getId());
        System.out.println("   ✓ Cloned Appointment ID: " + clonedAppointment.getId());
        System.out.println("   ✓ Cloned Doctor: " + clonedAppointment.getDoctor().getName());
        System.out.println("   ✓ Cloned Patient: " + clonedAppointment.getPatient().getName());
        
        // Verify nested objects are cloned
        clonedAppointment.getDoctor().setFee(9999.0);
        System.out.println("   ✓ After modifying cloned doctor fee:");
        System.out.println("     - Original doctor fee: $" + originalAppointment.getDoctor().getFee());
        System.out.println("     - Cloned doctor fee: $" + clonedAppointment.getDoctor().getFee());
        System.out.println();
    }

    private static void testStreamsAndLambdas() {
        System.out.println("--- Testing Streams & Lambdas ---");
        DoctorService doctorService = new DoctorService();

        // Add doctors with different specializations
        doctorService.addDoctor("Dr. Cardio1", 40, Specialization.CARDIOLOGY, 1500.0);
        doctorService.addDoctor("Dr. Cardio2", 45, Specialization.CARDIOLOGY, 1800.0);
        doctorService.addDoctor("Dr. Neuro1", 35, Specialization.NEUROLOGY, 2000.0);
        doctorService.addDoctor("Dr. Ped1", 38, Specialization.PEDIATRICS, 1200.0);

        // Test filterBySpecialization
        System.out.println("1. Filtering doctors by specialization (CARDIOLOGY)...");
        List<Doctor> cardiologists = doctorService.filterBySpecialization(Specialization.CARDIOLOGY);
        System.out.println("   ✓ Found " + cardiologists.size() + " cardiologist(s):");
        for (Doctor d : cardiologists) {
            System.out.println("     - " + d.getName() + " | Fee: $" + d.getFee());
        }

        // Test averageFee
        System.out.println("\n2. Calculating average consultation fee...");
        double avgFee = doctorService.averageFee();
        System.out.println("   ✓ Average fee: $" + String.format("%.2f", avgFee));
        
        // Test with empty list (should return 0.0)
        DoctorService emptyService = new DoctorService();
        double emptyAvg = emptyService.averageFee();
        System.out.println("   ✓ Average fee (empty service): $" + String.format("%.2f", emptyAvg));
        System.out.println();
    }
}

