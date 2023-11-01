package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Datban {
    private String Maban;
    private int Makhachhang;
    private String Ghichu;

    public Class_Table_Datban(String maban, int makhachhang, String ghichu) {
        Maban = maban;
        Makhachhang = makhachhang;
        Ghichu = ghichu;
    }

    public String getMaban() {
        return Maban;
    }

    public void setMaban(String maban) {
        Maban = maban;
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
