package config;



import exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USERNAME");
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");

    private static Database instance;
    private Connection connection;

    private Database() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database", e);
        }
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
