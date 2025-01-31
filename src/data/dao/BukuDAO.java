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

    public ArrayList<Buku> getAllBuku(String judul, String penulis, String penerbit) {
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
        if (!penerbit.trim().isEmpty()) {
            query.append(params.isEmpty() ? " WHERE" : " AND").append(" penerbit LIKE ?");
            params.add("%" + penerbit + "%");
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

    public Buku findBukuByID(int id){
        Buku buku = new Buku();
        String query = "select * from buku where id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    buku.setId(rs.getInt("id"));
                    buku.setJudul(rs.getString("judul"));
                    buku.setPenulis(rs.getString("penulis"));
                    buku.setTanggalTerbit(rs.getDate("tanggal_terbit"));
                    buku.setPenerbit(rs.getString("penerbit"));
                    buku.setStok(rs.getInt("stok"));
                }
                else buku = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BukuDAO.class.getName()).log(Level.SEVERE, null, ex);
            buku = null;
        }
        return buku;
    }
}
