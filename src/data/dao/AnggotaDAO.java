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
import model.Anggota;

/**
 *
 * @author ahmad
 */
public class AnggotaDAO {

    Connection conn;

    public AnggotaDAO() {
        conn = Koneksi.getKoneksi();
    }

    public ArrayList<Anggota> getAllAnggota(String nama) {
        ArrayList<Anggota> listAnggota = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM anggota");
        ArrayList<String> params = new ArrayList<>();

        if (!nama.trim().isEmpty()) {
            query.append(" WHERE nama LIKE ?");
            params.add("%" + nama + "%");
        }

        try (PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Anggota anggota = new Anggota();
                    anggota.setId(rs.getInt("id"));
                    anggota.setNama(rs.getString("nama"));
                    anggota.setEmail(rs.getString("email"));
                    anggota.setNomorTelepon(rs.getString("nomor_telepon"));
                    listAnggota.add(anggota);
                }
            }
        } catch (SQLException ex) {
            return null;
        }

        return listAnggota;
    }

    public Anggota findAnggotaByID(int id) {
        Anggota anggota = new Anggota();
        String query = "SELECT * FROM anggota WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    anggota.setId(rs.getInt("id"));
                    anggota.setNama(rs.getString("nama"));
                    anggota.setEmail(rs.getString("email"));
                    anggota.setNomorTelepon(rs.getString("nomor_telepon"));
                } else {
                    anggota = null;
                }
            }
        } catch (SQLException ex) {
            anggota = null;
        }
        return anggota;
    }

    public DefaultTableModel getModel(String nama) {
        ArrayList<Anggota> listAnggota = getAllAnggota(nama);
        Object[][] dataTabel = new Object[listAnggota.size()][4];

        for (int i = 0; i < listAnggota.size(); i++) {
            dataTabel[i][0] = listAnggota.get(i).getId();
            dataTabel[i][1] = listAnggota.get(i).getNama();
            dataTabel[i][2] = listAnggota.get(i).getEmail();
            dataTabel[i][3] = listAnggota.get(i).getNomorTelepon();
        }

        String[] columnName = {"ID", "Nama", "Email", "Nomor Telepon"};
        return new DefaultTableModel(dataTabel, columnName);
    }

    public boolean deleteAnggota(int id) {
        String qry = "DELETE FROM anggota WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(qry)) {
            ps.setInt(1, id);
            
            int result = ps.executeUpdate();
            
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addAnggota(Anggota anggota) {
        String query = "INSERT INTO anggota (nama, email, nomor_telepon) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getEmail());
            ps.setString(3, anggota.getNomorTelepon());

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(AnggotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editAnggota(Anggota anggota) {
        String qry = "UPDATE anggota SET nama=?, email=?, nomor_telepon=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(qry)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getEmail());
            ps.setString(3, anggota.getNomorTelepon());
            ps.setInt(4, anggota.getId());
            
            int result = ps.executeUpdate();
            
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
