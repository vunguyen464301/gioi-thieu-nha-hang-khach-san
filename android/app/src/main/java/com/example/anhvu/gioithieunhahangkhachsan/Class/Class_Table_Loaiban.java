package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Loaiban {
    private int maloaiban;
    private String hinhchung;
    private String tenban;
    private int soluong;

    public Class_Table_Loaiban(int maloaiban, String hinhchung, String tenban, int soluong) {
        this.maloaiban = maloaiban;
        this.hinhchung = hinhchung;
        this.tenban = tenban;
        this.soluong = soluong;
    }

    public int getMaloaiban() {
        return maloaiban;
    }

    public void setMaloaiban(int maloaiban) {
        this.maloaiban = maloaiban;
    }

    public String getHinhchung() {
        return hinhchung;
    }

    public void setHinhchung(String hinhchung) {
        this.hinhchung = hinhchung;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
