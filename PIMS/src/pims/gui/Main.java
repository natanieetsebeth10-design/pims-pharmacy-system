package pims.gui;

import pims.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Tries to get a database connection from the utility class
            Connection conn = DBConnection.getConnection();
            
            // Checks if the connection is active and valid
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to the database successfully!");
                
                // Launch the login window
                java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
            }
        } catch (SQLException e) {
            // Prints an error message and the stack trace if the connection doesnt work
            System.out.println("Connection was unsucessfull due to:");
            e.printStackTrace();
        }
    }
}