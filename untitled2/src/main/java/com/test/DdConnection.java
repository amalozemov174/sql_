package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DdConnection {
    private String url = "";
    private String user = "";
    private String password = "";
    public DdConnection() {
        url = "jdbc:postgresql://localhost:5432/lesson1";
        user = "postgres";
        password = "postgres";
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
