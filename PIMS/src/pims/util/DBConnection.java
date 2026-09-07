package pims.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/pims_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "3t$383tH";
    
    // Stores the active database connection
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        // Checks if a connection needs to be created or recreated
        if (connection == null || connection.isClosed()) {
            try {
                // Loads the MySQL driver class
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // Throws an error if the driver is missing
                throw new SQLException("MySQL JDBC Driver not found on classpath.", e);
            }
            // Creates the actual database connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        // Returns the active connection
        return connection;
    }
}
