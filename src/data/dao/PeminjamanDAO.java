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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Peminjaman;

/**
 *
 * @author ahmad
 */
public class PeminjamanDAO {
    
    Connection conn;

    public PeminjamanDAO() {
        conn = Koneksi.getKoneksi();
    }

    public ArrayList<Peminjaman> getAllPeminjaman(String namaAnggota, String namaPetugas) {
        ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT p.id, a.nama AS nama_anggota, p.tanggal_peminjaman, p.tanggal_pengembalian, pet.nama AS petugas " +
            "FROM peminjaman p " +
            "JOIN anggota a ON p.id_anggota = a.id " +
            "JOIN petugas pet ON p.id_petugas = pet.id"
        );
        ArrayList<String> params = new ArrayList<>();

        if (!namaAnggota.trim().isEmpty()) {
            query.append(" WHERE a.nama LIKE ?");
            params.add("%" + namaAnggota + "%");
        }
        if (!namaPetugas.trim().isEmpty()) {
            query.append(params.isEmpty() ? " WHERE" : " AND").append(" pet.nama LIKE ?");
            params.add("%" + namaPetugas + "%");
        }

        try (PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Peminjaman peminjaman = new Peminjaman();
                    peminjaman.setId(rs.getInt("id"));
                    peminjaman.setNamaAnggota(rs.getString("nama_anggota"));
                    peminjaman.setTanggalPeminjaman(rs.getDate("tanggal_peminjaman"));
                    peminjaman.setTanggalPengembalian(rs.getDate("tanggal_pengembalian"));
                    peminjaman.setNamaPetugas(rs.getString("petugas"));
                    listPeminjaman.add(peminjaman);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listPeminjaman;
    }

    public Peminjaman findPeminjamanByID(int id) {
        Peminjaman peminjaman = new Peminjaman();
        String query = "SELECT p.id, a.id AS id_anggota, a.nama AS nama_anggota, p.tanggal_peminjaman, p.tanggal_pengembalian, pet.nama AS petugas " +
                       "FROM peminjaman p " +
                       "JOIN anggota a ON p.id_anggota = a.id " +
                       "JOIN petugas pet ON p.id_petugas = pet.id " +
                       "WHERE p.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    peminjaman.setId(rs.getInt("id"));
                    peminjaman.setIdAnggota(rs.getInt("id_anggota"));
                    peminjaman.setNamaAnggota(rs.getString("nama_anggota"));
                    peminjaman.setTanggalPeminjaman(rs.getDate("tanggal_peminjaman"));
                    peminjaman.setTanggalPengembalian(rs.getDate("tanggal_pengembalian"));
                    peminjaman.setNamaPetugas(rs.getString("petugas"));
                } else {
                    peminjaman = null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
            peminjaman = null;
        }
        return peminjaman;
    }

    public DefaultTableModel getModel(String namaAnggota, String namaPetugas) {
        ArrayList<Peminjaman> listPeminjaman = getAllPeminjaman(namaAnggota, namaPetugas);
        Object[][] dataTabel = new Object[listPeminjaman.size()][5];

        for (int i = 0; i < listPeminjaman.size(); i++) {
            dataTabel[i][0] = listPeminjaman.get(i).getId();
            dataTabel[i][1] = listPeminjaman.get(i).getNamaAnggota();
            dataTabel[i][2] = listPeminjaman.get(i).getTanggalPeminjaman();
            dataTabel[i][3] = listPeminjaman.get(i).getTanggalPengembalian();
            dataTabel[i][4] = listPeminjaman.get(i).getNamaPetugas();
        }

        String[] columnName = {"ID", "Nama Anggota", "Tanggal Pinjam", "Tanggal Kembali", "Petugas"};
        return new DefaultTableModel(dataTabel, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    
    public int addPeminjaman(Peminjaman peminjaman) {
        String query = "INSERT INTO peminjaman(id_anggota, id_petugas) VALUES(?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, peminjaman.getIdAnggota());
            ps.setInt(2, peminjaman.getIdPetugas());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()) {
                        return rs.getInt(1);
                    }
                    else return -1;
                }
            }
            else return -1;
        } catch (SQLException ex) {
            Logger.getLogger(PeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public boolean returnPeminjaman(int idPeminjaman) {
    String query = "UPDATE peminjaman SET tanggal_pengembalian = CURRENT_DATE WHERE id = ? AND tanggal_pengembalian IS NULL";
    
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idPeminjaman);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0; // Jika ada baris yang terupdate, berarti sukses
    } catch (SQLException ex) {
        Logger.getLogger(PeminjamanDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}

}
