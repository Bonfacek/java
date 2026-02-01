package com.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
    static final String DB_URL = "jdbc:mariadb://localhost:3306/devDB";
    static final String DB_USER = "karanis";
    static final String DB_PASS = "karanisDB1";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/devDB", "karanis", "karanisDB1");
    }
}
