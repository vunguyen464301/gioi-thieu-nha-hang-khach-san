package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Ban {
    private String maban;
    private String mota;
    private int maloaiban;
    private String tinhtrang;

    public Class_Table_Ban(String maban, String mota, int maloaiban, String tinhtrang) {
        this.maban = maban;
        this.mota = mota;
        this.maloaiban = maloaiban;
        this.tinhtrang = tinhtrang;
    }

    public String getMaban() {
        return maban;
    }

    public void setMaban(String maban) {
        this.maban = maban;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getMaloaiban() {
        return maloaiban;
    }

    public void setMaloaiban(int maloaiban) {
        this.maloaiban = maloaiban;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}
