package HospitalManagemnetsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;  // Import PreparedStatement
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;

    // Creating constructor and passing arguments
    public Patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addpatients() {
        System.out.println("Enter patient's name: ");
        String name = scanner.next();
        System.out.println("Enter patient's age: ");
        int age = scanner.nextInt();
        System.out.println("Enter patient's gender:");
        String gender = scanner.next();

        // Try-catch block to handle SQL exceptions
        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Setting the parameters for the query
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);

            // Executing the query
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewpatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            // Iterating over the results and printing them
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("ID: %d, Name: %s, Age: %d, Gender: %s%n", id, name, age, gender);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getpatientsByid(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("ID: %d, Name: %s, Age: %d, Gender: %s%n", id, name, age, gender);
                return true;
            } else {
                System.out.println("Patient not found.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}