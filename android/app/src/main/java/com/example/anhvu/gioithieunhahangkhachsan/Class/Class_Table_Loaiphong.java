package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Loaiphong {
    private int maloaiphong;
    private String hinhchung;
    private String tenphong;
    private int soluong;

    public Class_Table_Loaiphong(int maloaiphong, String hinhchung, String tenphong, int soluong) {
        this.maloaiphong = maloaiphong;
        this.hinhchung = hinhchung;
        this.tenphong = tenphong;
        this.soluong = soluong;
    }

    public int getMaloaiphong() {
        return maloaiphong;
    }

    public void setMaloaiphong(int maloaiphong) {
        this.maloaiphong = maloaiphong;
    }

    public String getHinhchung() {
        return hinhchung;
    }

    public void setHinhchung(String hinhchung) {
        this.hinhchung = hinhchung;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
