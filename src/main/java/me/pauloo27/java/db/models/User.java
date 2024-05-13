package me.pauloo27.java.db.models;

public class User {
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static User fromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("username"));
    }
}
