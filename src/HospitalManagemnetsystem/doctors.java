package HospitalManagemnetsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class doctors {
    private Connection connection;
    private Scanner scanner;


    // Creating constructor and passing arguments
    public doctors(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            // Iterating over the results and printing them
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("ID: %d, Name: %s, Specialization: %s%n", id, name, specialization);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("ID: %d, Name: %s, Specialization: %s%n", id, name, specialization);
                return true;
            } else {
                System.out.println("Doctor not found.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}