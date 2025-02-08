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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Buku;
import model.DetailPeminjaman;
import model.Peminjaman;

/**
 *
 * @author ahmad
 */
public class DetailPeminjamanDAO {
    Connection conn;
    
    public DetailPeminjamanDAO(){
        conn = Koneksi.getKoneksi();
    }
    
    public ArrayList<DetailPeminjaman> getListDetailPeminjamanByIdPeminjaman(int idPeminjaman) {
        ArrayList<DetailPeminjaman> listDetail = new ArrayList<>();
        String query = "SELECT b.judul, dp.jumlah " +
                       "FROM detail_peminjaman dp " +
                       "JOIN buku b ON dp.id_buku = b.id " +
                       "WHERE dp.id_peminjaman = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPeminjaman);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailPeminjaman detail = new DetailPeminjaman();
                    detail.setJudul(rs.getString("judul"));
                    detail.setJumlah(rs.getInt("jumlah"));
                    listDetail.add(detail);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailPeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listDetail;
    }
    
    public DetailPeminjaman findDetailPeminjamanById(int id) {
        DetailPeminjaman detail = new DetailPeminjaman();
        String query = "SELECT dp.id_peminjaman, dp.id_buku, b.judul, dp.jumlah " +
                       "FROM detail_peminjaman dp " +
                       "JOIN buku b ON dp.id_buku = b.id " +
                       "WHERE dp.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detail.setIdPeminjaman(rs.getInt("id_peminjaman"));
                    detail.setIdBuku(rs.getInt("id_buku"));
                    detail.setJudul(rs.getString("judul"));
                    detail.setJumlah(rs.getInt("jumlah"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailPeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return detail;
    }

    public DefaultTableModel getModel(int idPeminjaman) {
        ArrayList<DetailPeminjaman> listDetail = getListDetailPeminjamanByIdPeminjaman(idPeminjaman);
        Object[][] dataTabel = new Object[listDetail.size()][2];

        for (int i = 0; i < listDetail.size(); i++) {
            dataTabel[i][0] = listDetail.get(i).getJudul();
            dataTabel[i][1] = listDetail.get(i).getJumlah();
        }

        String[] columnNames = {"Judul Buku", "Jumlah"};
        return new DefaultTableModel(dataTabel, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    
    public Boolean addDetailPeminjaman(DetailPeminjaman detail) {
        String query = "INSERT INTO detail_peminjaman(id_peminjaman, id_buku, jumlah) VALUES(?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, detail.getIdPeminjaman());
            ps.setInt(2, detail.getIdBuku());
            ps.setInt(3, detail.getJumlah());
            
            int result = ps.executeUpdate();
            
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
