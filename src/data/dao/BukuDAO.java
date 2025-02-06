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
import model.Buku;

/**
 *
 * @author ahmad
 */
public class BukuDAO {

    Connection conn;

    public BukuDAO() {
        conn = Koneksi.getKoneksi();
    }

    public ArrayList<Buku> getAllBuku(String judul, String penulis) {
        ArrayList<Buku> listBuku = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM buku");
        ArrayList<String> params = new ArrayList<>();

        if (!judul.trim().isEmpty()) {
            query.append(" WHERE judul LIKE ?");
            params.add("%" + judul + "%");
        }
        if (!penulis.trim().isEmpty()) {
            query.append(params.isEmpty() ? " WHERE" : " AND").append(" penulis LIKE ?");
            params.add("%" + penulis + "%");
        }

        try (PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Buku buku = new Buku();
                    buku.setId(rs.getInt("id"));
                    buku.setJudul(rs.getString("judul"));
                    buku.setPenulis(rs.getString("penulis"));
                    buku.setTanggalTerbit(rs.getDate("tanggal_terbit"));
                    buku.setPenerbit(rs.getString("penerbit"));
                    buku.setStok(rs.getInt("stok"));
                    listBuku.add(buku);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BukuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listBuku;
    }

    public Buku findBukuByID(int id) {
        Buku buku = new Buku();
        String query = "select * from buku where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    buku.setId(rs.getInt("id"));
                    buku.setJudul(rs.getString("judul"));
                    buku.setPenulis(rs.getString("penulis"));
                    buku.setTanggalTerbit(rs.getDate("tanggal_terbit"));
                    buku.setPenerbit(rs.getString("penerbit"));
                    buku.setStok(rs.getInt("stok"));
                } else {
                    buku = null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BukuDAO.class.getName()).log(Level.SEVERE, null, ex);
            buku = null;
        }
        return buku;
    }

    public DefaultTableModel getModel(String judul, String penulis) {
        ArrayList<Buku> listBuku = getAllBuku(judul, penulis);
        Object[][] dataTabel = new Object[listBuku.size()][6];

        for (int i = 0; i < listBuku.size(); i++) {
            dataTabel[i][0] = listBuku.get(i).getId();
            dataTabel[i][1] = listBuku.get(i).getJudul();
            dataTabel[i][2] = listBuku.get(i).getPenulis();
            dataTabel[i][3] = listBuku.get(i).getTanggalTerbit();
            dataTabel[i][4] = listBuku.get(i).getPenerbit();
            dataTabel[i][5] = listBuku.get(i).getStok();
        }

        String[] columnName = {"ID", "Judul", "Penulis", "Tanggal Terbit", "Penerbit", "Stok"};
        return new DefaultTableModel(dataTabel, columnName);
    }

    public boolean addBuku(Buku buku) {
        String query = "INSERT INTO buku (judul, penulis, tanggal_terbit, penerbit, stok) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setDate(3, buku.getTanggalTerbit()); // Convert java.util.Date to java.sql.Date
            ps.setString(4, buku.getPenerbit());
            ps.setInt(5, buku.getStok());

            int result = ps.executeUpdate();

            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BukuDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editBuku(Buku buku) {
        String query = "UPDATE buku SET judul = ?, penulis = ?, tanggal_terbit = ?, penerbit = ?, stok = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            // Set nilai parameter untuk query
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setDate(3, new java.sql.Date(buku.getTanggalTerbit().getTime())); // Convert java.util.Date to java.sql.Date
            ps.setString(4, buku.getPenerbit());
            ps.setInt(5, buku.getStok());
            ps.setInt(6, buku.getId()); // ID buku yang akan diubah

            // Eksekusi query untuk memperbarui data
            int result = ps.executeUpdate();

            // Jika ada baris yang terpengaruh (baris yang berhasil diupdate > 0), return true
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BukuDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Jika terjadi kesalahan, return false
        }
    }

}
