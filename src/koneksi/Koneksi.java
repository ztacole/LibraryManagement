/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ahmad
 */
public class Koneksi {
    private static Connection conn = null;

    public static Connection getKoneksi(){
        if (conn != null){
            return conn;
        }
        String dbUrl = "jdbc:mysql://localhost:3306/datasiswa";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(dbUrl, "root", "");
            System.out.println("Success Connection");
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Failed Connection : "+e);
        }
        return conn;
    }

    public static void main(String[] args) {
        getKoneksi();
    }
}
