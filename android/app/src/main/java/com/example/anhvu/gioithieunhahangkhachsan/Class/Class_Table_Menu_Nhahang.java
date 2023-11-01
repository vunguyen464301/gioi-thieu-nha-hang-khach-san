package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Menu_Nhahang {
    private int mamon;
    private String hinhchung;
    private String tenmonan;
    private int dongia;

    public Class_Table_Menu_Nhahang(int mamon, String hinhchung, String tenmonan, int dongia) {
        this.mamon = mamon;
        this.hinhchung = hinhchung;
        this.tenmonan = tenmonan;
        this.dongia = dongia;
    }

    public int getMamon() {
        return mamon;
    }

    public void setMamon(int mamon) {
        this.mamon = mamon;
    }

    public String getHinhchung() {
        return hinhchung;
    }

    public void setHinhchung(String hinhchung) {
        this.hinhchung = hinhchung;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }
}
