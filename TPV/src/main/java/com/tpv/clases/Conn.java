package com.tpv.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    public static Connection con(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpv", "root", "2003");
            //Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3305/tpv", "root", "root");

            return con;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
