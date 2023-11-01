package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Datphong {
    private String Maphong;
    private int Makhachhang;
    private String Ghichu;

    public Class_Table_Datphong(String maphong, int makhachhang, String ghichu) {
        Maphong = maphong;
        Makhachhang = makhachhang;
        Ghichu = ghichu;
    }

    public String getMaphong() {
        return Maphong;
    }

    public void setMaphong(String maphong) {
        Maphong = maphong;
    }

    public int getMakhachhang() {
        return Makhachhang;
    }

    public void setMakhachhang(int makhachhang) {
        Makhachhang = makhachhang;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }
}
