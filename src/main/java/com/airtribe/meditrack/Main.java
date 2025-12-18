package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static Scanner scanner;

    static {
        System.out.println("MediTrack Initialized");
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    addPatient();
                    break;
                case 3:
                    createAppointment();
                    break;
                case 4:
                    viewAll();
                    break;
                case 5:
                    System.out.println("Exiting MediTrack. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== MediTrack Menu ===");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Patient");
        System.out.println("3. Create Appointment");
        System.out.println("4. View All");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addDoctor() {
        System.out.println("\n--- Add Doctor ---");
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter doctor age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Doctor not added.");
            return;
        }

        System.out.println("Select specialization:");
        Specialization[] specializations = Specialization.values();
        for (int i = 0; i < specializations.length; i++) {
            System.out.println((i + 1) + ". " + specializations[i]);
        }
        System.out.print("Enter specialization number: ");
        int specChoice;
        try {
            specChoice = Integer.parseInt(scanner.nextLine().trim());
            if (specChoice < 1 || specChoice > specializations.length) {
                System.out.println("Invalid specialization. Doctor not added.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid specialization. Doctor not added.");
            return;
        }

        System.out.print("Enter consultation fee: ");
        double fee;
        try {
            fee = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid fee. Doctor not added.");
            return;
        }

        Doctor doctor = doctorService.addDoctor(name, age, specializations[specChoice - 1], fee);
        System.out.println("Doctor added successfully! ID: " + doctor.getId());
    }

    private static void addPatient() {
        System.out.println("\n--- Add Patient ---");
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter patient age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Patient not added.");
            return;
        }

        System.out.print("Enter symptoms: ");
        String symptoms = scanner.nextLine().trim();

        Patient patient = patientService.addPatient(name, age, symptoms);
        System.out.println("Patient added successfully! ID: " + patient.getId());
    }

    private static void createAppointment() {
        System.out.println("\n--- Create Appointment ---");
        
        List<Doctor> doctors = doctorService.listDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available. Please add a doctor first.");
            return;
        }

        List<Patient> patients = patientService.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients available. Please add a patient first.");
            return;
        }

        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.get(i);
            System.out.println((i + 1) + ". " + doc.getName() + " (" + doc.getSpecialization() + ")");
        }
        System.out.print("Select doctor number: ");
        int docChoice;
        try {
            docChoice = Integer.parseInt(scanner.nextLine().trim());
            if (docChoice < 1 || docChoice > doctors.size()) {
                System.out.println("Invalid doctor selection. Appointment not created.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid doctor selection. Appointment not created.");
            return;
        }

        System.out.println("Available Patients:");
        for (int i = 0; i < patients.size(); i++) {
            Patient pat = patients.get(i);
            System.out.println((i + 1) + ". " + pat.getName() + " (ID: " + pat.getId() + ")");
        }
        System.out.print("Select patient number: ");
        int patChoice;
        try {
            patChoice = Integer.parseInt(scanner.nextLine().trim());
            if (patChoice < 1 || patChoice > patients.size()) {
                System.out.println("Invalid patient selection. Appointment not created.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid patient selection. Appointment not created.");
            return;
        }

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Appointment not created.");
            return;
        }

        Doctor selectedDoctor = doctors.get(docChoice - 1);
        Patient selectedPatient = patients.get(patChoice - 1);
        
        Appointment appointment = appointmentService.createAppointment(selectedDoctor, selectedPatient, date);
        System.out.println("Appointment created successfully! ID: " + appointment.getId());
    }

    private static void viewAll() {
        System.out.println("\n--- View All ---");
        
        System.out.println("\nDoctors:");
        List<Doctor> doctors = doctorService.listDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            for (Doctor doctor : doctors) {
                System.out.println("ID: " + doctor.getId() + 
                    ", Name: " + doctor.getName() + 
                    ", Age: " + doctor.getAge() + 
                    ", Specialization: " + doctor.getSpecialization() + 
                    ", Fee: " + doctor.getFee());
            }
        }

        System.out.println("\nPatients:");
        List<Patient> patients = patientService.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            for (Patient patient : patients) {
                System.out.println("ID: " + patient.getId() + 
                    ", Name: " + patient.getName() + 
                    ", Age: " + patient.getAge() + 
                    ", Symptoms: " + patient.getSymptoms());
            }
        }

        System.out.println("\nAppointments:");
        List<Appointment> appointments = appointmentService.listAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println("ID: " + appointment.getId() + 
                    ", Doctor: " + appointment.getDoctor().getName() + 
                    ", Patient: " + appointment.getPatient().getName() + 
                    ", Date: " + appointment.getDate() + 
                    ", Status: " + appointment.getStatus());
            }
        }
    }
}
