package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Tables_Khachhang_Administrator {
    private int ma;
    private String sodienthoai;

    public Class_Tables_Khachhang_Administrator(int ma, String sodienthoai) {
        this.ma = ma;
        this.sodienthoai = sodienthoai;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
}
