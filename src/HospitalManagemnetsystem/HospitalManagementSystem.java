package HospitalManagemnetsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class HospitalManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kiit";

    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Loading the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully!");

            // Creating objects for patients and doctors classes
            Patients patientHandler = new Patients(connection, scanner);
            doctors doctorHandler = new doctors(connection, scanner);

            boolean exit = false;
            while (!exit) {
                System.out.println("\nHOSPITAL MANAGEMENT SYSTEM:");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointments");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                switch (choice) {
                    case 1:
                        patientHandler.addpatients();
                        break;

                    case 2:
                        patientHandler.viewpatients();
                        break;

                    case 3:
                        doctorHandler.viewDoctors();
                        break;

                    case 4:
                        bookAppointment(patientHandler, doctorHandler, connection, scanner);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (scanner != null) {
                    scanner.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void bookAppointment(Patients patientHandler, doctors doctorHandler, Connection connection, Scanner scanner) {
        System.out.println("Enter patient ID:");
        int patientID = scanner.nextInt();
        System.out.println("Enter doctor ID:");
        int doctorID = scanner.nextInt();
        System.out.println("Enter appointment date (yyyy-MM-dd):");
        String appointmentDate = scanner.next();

        try {
            if (patientHandler.getpatientsByid(patientID) && doctorHandler.getDoctorById(doctorID)) {
                if (checkDoctorAvailability(doctorID, appointmentDate, connection)) {
                    String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(appointmentQuery)) {
                        pstmt.setInt(1, patientID);
                        pstmt.setInt(2, doctorID);
                        pstmt.setString(3, appointmentDate);

                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Appointment booked successfully.");
                        } else {
                            System.out.println("Failed to book appointment.");
                        }
                    }
                } else {
                    System.out.println("Doctor is not available on this date.");
                }
            } else {
                System.out.println("Either doctor or patient does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error while booking appointment.");
            e.printStackTrace();
        }
    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        // This method should be implemented to check the availability of the doctor.
        // For now, we will just return true for simplicity.
        return true;
    }
}