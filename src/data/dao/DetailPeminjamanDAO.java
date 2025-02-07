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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.Koneksi;
import model.DetailPeminjaman;

/**
 *
 * @author ahmad
 */
public class DetailPeminjamanDAO {
    Connection conn;
    
    public DetailPeminjamanDAO(){
        conn = Koneksi.getKoneksi();
    }
    
    public ArrayList<DetailPeminjaman> getDetailPeminjamanById(int idPeminjaman) {
        ArrayList<DetailPeminjaman> listDetailPeminjaman = new ArrayList<>();
        String query = "SELECT * from detail_peminjaman where id_peminjaman = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPeminjaman);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailPeminjaman detailPeminjaman = new DetailPeminjaman();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailPeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listDetailPeminjaman;
    }
}
