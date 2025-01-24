/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static koneksi.koneksi.getKoneksi;
import model.anggota;
import model.petugas;

/**
 *
 * @author anandakeiza
 */
public class loginpage {
    
    Connection kon;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<petugas> listPetugas;
    anggota Anggota;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    
    public loginpage(){
        kon = getKoneksi();
    
    }
    
    public ArrayList<petugas> getListPetugas(){
        try{
            listPetugas = new ArrayList<>();
            ps = kon.prepareStatement("SELECT * FROM Guru", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                guru = new guru();
                guru.setNik(rs.getString("nik"));
                guru.setNama(rs.getString("nama"));
                guru.setJenkel(rs.getString("jenkel"));
                guru.setTmpLahir(rs.getString("tmpLahir"));
                guru.setTglLahir(rs.getDate("tglLahir"));
                guru.setAlamat(rs.getString("alamat"));
                guru.setTelpon(rs.getString("telpon"));
                guru.setUser(rs.getString("user"));
                guru.setFoto(rs.getString("foto"));
          

                //lanjutkan...

                listGuru.add(guru); //tambahkan data siswa ke list siswa
            }
        }catch (SQLException se){
            System.out.println("Eror : " + se.getMessage());
        }
        return listGuru;
    }
    
    
    
}
