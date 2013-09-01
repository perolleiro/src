package com.project.dao;

import java.sql.*;

public class postgreSQL {
	public final static Connection postgreSQLConn () throws Exception {
		Class.forName("com.postgresql.org").newInstance();
        // Construct the database address
        String dbaseURL = "jdbc:postgresql://localhost:5432/Storage";
        // Make the database connection
        Connection dbConnection = 
            DriverManager.getConnection(dbaseURL, "postgresql", "Color");
        
        return dbConnection;
	}
}
