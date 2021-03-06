package com.mwm.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.ResultSet;
//import java.sql.Statement;

public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:sqlserver://localhost;databaseName=SMGPMDemo;integratedSecurity=true;";
    //public static final String URL = "jdbc:mysql://localhost:3306/SMGPMDemo"; // sting not appropriate for Ms SQL
    public static final String USER = "SMGPMDemo";
    public static final String PASSWORD = "SMGPMDemo123";
    public static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
   
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }  
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
}