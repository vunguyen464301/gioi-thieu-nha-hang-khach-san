package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Tinnhan {
    private int Sothutu;
    private int Mahopthoai;
    private int Makhachhang;
    private String Noidung;

    public Class_Table_Tinnhan(int sothutu, int mahopthoai, int makhachhang, String noidung) {
        Sothutu = sothutu;
        Mahopthoai = mahopthoai;
        Makhachhang = makhachhang;
        Noidung = noidung;
    }

    public int getSothutu() {
        return Sothutu;
    }

    public void setSothutu(int sothutu) {
        Sothutu = sothutu;
    }

    public int getMahopthoai() {
        return Mahopthoai;
    }

    public void setMahopthoai(int mahopthoai) {
        Mahopthoai = mahopthoai;
    }

    public int getMakhachhang() {
        return Makhachhang;
    }

    public void setMakhachhang(int makhachhang) {
        Makhachhang = makhachhang;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String noidung) {
        Noidung = noidung;
    }
}
