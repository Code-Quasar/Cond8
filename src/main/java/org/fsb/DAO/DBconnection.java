package org.fsb.DAO;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBconnection {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");

        System.out.println(url + username + password);

        String query = "SELECT * FROM test";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Connected to the database!");

            while (resultSet.next()) {
                System.out.println("Column 1: " + resultSet.getString(1)); // Replace with actual column name or index
                System.out.println("Column 2: " + resultSet.getString(2)); // Replace with actual column name or index
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
