package me.pauloo27.java.db;

import java.sql.Connection;
import java.sql.SQLException;

public class Schema {
    public static void createTables(Connection conn) throws SQLException {
        conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS public.user (id SERIAL PRIMARY KEY, username VARCHAR(255) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL)");
        conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS public.product (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, price DECIMAL(10, 2) NOT NULL, amount INT NOT NULL)");
    }
}
