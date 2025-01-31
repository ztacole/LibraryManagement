/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anandakeiza
 */
public class koneksi {
    private static Connection connection = null;
    public static Connection getKoneksi() {
        if (connection != null) {
            return connection;
        } else {
            String dbUrl = "jdbc:mysql://localhost:3306/datasiswa";
            String username = "root"; // Ganti dengan username MySQL Anda
            String password = ""; // Ganti dengan password MySQL Anda
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(dbUrl, username, password);
                System.out.println("Koneksi Sukses");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Driver Tidak Ditemukan: " + e.getMessage());
            }
            return connection;
        }
    }
    public static void main(String[] args) {
        getKoneksi();
    }
}
