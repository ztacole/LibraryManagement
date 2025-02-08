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
import javax.swing.table.DefaultTableModel;
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
    
    public ArrayList<DetailPeminjaman> getListDetailPeminjamanById(int idPeminjaman) {
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

    public DefaultTableModel getModel(int idPeminjaman) {
        ArrayList<DetailPeminjaman> listDetail = getListDetailPeminjamanById(idPeminjaman);
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
}
