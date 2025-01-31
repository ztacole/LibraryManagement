/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author ahmad
 */
public class DetailPeminjaman {
    private int id, idPeminjaman, idBuku, jumlah;
    
    //Peminjaman
    private int idAnggota, idPetugas, denda;
    private Date tanggalPeminjaman, tanggalPengembalian;
    
    //Buku
    private String judul, penulis, penerbit;
    private Date tanggalTerbit;
    private int stok;
    
    //Anggota
    private String namaAnggota, emailAnggota, passwordAnggota, nomorTeleponAnggota;
    
    //Petugas
    private String namaPetugas, username, passwordPetugas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public int getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(int idPetugas) {
        this.idPetugas = idPetugas;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    public Date getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(Date tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public Date getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(Date tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public Date getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(Date tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getEmailAnggota() {
        return emailAnggota;
    }

    public void setEmailAnggota(String emailAnggota) {
        this.emailAnggota = emailAnggota;
    }

    public String getPasswordAnggota() {
        return passwordAnggota;
    }

    public void setPasswordAnggota(String passwordAnggota) {
        this.passwordAnggota = passwordAnggota;
    }

    public String getNomorTeleponAnggota() {
        return nomorTeleponAnggota;
    }

    public void setNomorTeleponAnggota(String nomorTeleponAnggota) {
        this.nomorTeleponAnggota = nomorTeleponAnggota;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordPetugas() {
        return passwordPetugas;
    }

    public void setPasswordPetugas(String passwordPetugas) {
        this.passwordPetugas = passwordPetugas;
    }
}
