package com.tpv.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Gestiontpv {

    public Gestiontpv(){

    }
    public Connection Con(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpv", "root", "2003");
            return con;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
