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
public class Koneksi {
    private static Connection connection = null;
    public static Connection getKoneksi() {
        if (connection != null) {
            return connection;
        } else {
            String dbUrl = "jdbc:mysql://localhost:3306/library24";
            String username = "root";
            String password = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
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
