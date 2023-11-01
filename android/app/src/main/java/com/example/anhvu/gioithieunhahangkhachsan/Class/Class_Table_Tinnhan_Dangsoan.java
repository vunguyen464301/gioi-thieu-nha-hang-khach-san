package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Tinnhan_Dangsoan {
    private int Sothutu;
    private int Mahopthoai;
    private int Makhachhang;
    //vì là tình trạng có hoặc không nên để kiểu int cho nhanh
    private int Tinhtrang;

    public Class_Table_Tinnhan_Dangsoan(int sothutu, int mahopthoai, int makhachhang, int tinhtrang) {
        Sothutu = sothutu;
        Mahopthoai = mahopthoai;
        Makhachhang = makhachhang;
        Tinhtrang = tinhtrang;
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

    public int getTinhtrang() {
        return Tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        Tinhtrang = tinhtrang;
    }
}
