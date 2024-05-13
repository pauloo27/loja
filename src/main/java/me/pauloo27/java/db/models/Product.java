package me.pauloo27.java.db.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;

    public Product(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getAmount() {
        return this.amount;
    }

    public static Product fromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        return new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price"),
                resultSet.getInt("amount"));
    }
}
