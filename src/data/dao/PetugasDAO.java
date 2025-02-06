/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.Koneksi;
import model.Petugas;

/**
 *
 * @author ahmad
 */
public class PetugasDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    public PetugasDAO(){
        conn = Koneksi.getKoneksi();
    }
    
    public Petugas findPetugas(String username, String password){
        Petugas petugas = new Petugas();
        String query = "select * from petugas where username = ? and password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()){
                petugas.setId(rs.getInt("id"));
                petugas.setNama(rs.getString("nama"));
                petugas.setUsername(rs.getString("username"));
                petugas.setPassword(rs.getString("password"));
            }
            else petugas = null;
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return petugas;
    }
}
