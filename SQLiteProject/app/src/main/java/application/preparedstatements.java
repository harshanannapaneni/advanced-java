package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class preparedstatements {
    private static final Logger logger = LoggerFactory.getLogger(preparedstatements.class);

    public static void main(String[] args) {
        int[] ids = {10, 20, 30};
        String[] names = {"John Doe", "William Smith", "Robert McKinney"};

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement insertStmt = null;
        ResultSet result = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC driver loaded successfully.");

            // Connect to MySQL server without specifying the database
            String dbUrl = "jdbc:mysql://localhost:3306/";
            conn = DriverManager.getConnection(dbUrl, "harsha", "Admin@123");
            logger.info("Connected to the MySQL server successfully.");

            stmt = conn.createStatement();

            // Create the database if it does not exist
            String createDbSql = "CREATE DATABASE IF NOT EXISTS people";
            stmt.execute(createDbSql);
            logger.info("Database 'people' checked/created successfully.");

            // Connect to the 'people' database
            dbUrl = "jdbc:mysql://localhost:3306/people";
            conn = DriverManager.getConnection(dbUrl, "harsha", "Admin@123");
            conn.setAutoCommit(false);
            logger.info("Connected to the 'people' database successfully.");

            stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name TEXT NOT NULL)";
            stmt.execute(sql);
            logger.info("User table successfully created.");

            sql = "INSERT INTO user (id, name) VALUES(?, ?)";
            insertStmt = conn.prepareStatement(sql);

            for (int i = 0; i < ids.length; i++) {
                insertStmt.setInt(1, ids[i]);
                insertStmt.setString(2, names[i]);
                insertStmt.executeUpdate();
            }

            conn.commit();
            logger.info("Records inserted successfully.");

            String querySQL = "SELECT * FROM user";
            result = stmt.executeQuery(querySQL);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                System.out.println(id + " " + name);
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("An error occurred.", e);
            if (conn != null) {
                try {
                    conn.rollback();
                    logger.info("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    logger.error("Error during rollback.", rollbackEx);
                }
            }
        } finally {
            try {
                if (result != null) result.close();
                if (insertStmt != null) insertStmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                logger.info("Resources closed successfully.");
            } catch (SQLException e) {
                logger.error("Error closing resources.", e);
            }
        }
    }
}
