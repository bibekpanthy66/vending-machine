package com.example.vendingmachine;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToDB {
    public static Connection connectToDB() {
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String databaseName="vending_machine";
        String myUrl = "jdbc:mysql://localhost:3306/"+databaseName;
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        try {
            String username="root";
            String password="Darshan@1962";
            conn = DriverManager.getConnection(myUrl, username, password);
        } catch (Exception e) {

        }
        return conn;
    }
}
