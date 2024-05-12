package me.pauloo27.java.db;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

public class DB {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = new FileInputStream("./db.properties");
                prop.load(inputStream);
                String dbUrl = prop.getProperty("db.url");
                String user = prop.getProperty("db.user");
                String password = prop.getProperty("db.password");
                connection = DriverManager.getConnection(dbUrl, user, password);
                Schema.createTables(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
        }
    }

    public static void setConnection(Connection conn) {
        DB.connection = conn;
    }
}
