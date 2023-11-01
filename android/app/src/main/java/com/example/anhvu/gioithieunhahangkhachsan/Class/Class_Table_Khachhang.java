package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Khachhang {
    int Makhachhang;
    String Sodienthoai;
    String Hinhanh;
    String Tenkhachhang;
    String Ngaythangnamsinh;
    String Diachi;
    String Matkhau;

    public Class_Table_Khachhang() {
    }

    public Class_Table_Khachhang(int makhachhang, String sodienthoai, String hinhanh, String tenkhachhang, String ngaythangnamsinh, String diachi, String matkhau) {
        Makhachhang = makhachhang;
        Sodienthoai = sodienthoai;
        Hinhanh = hinhanh;
        Tenkhachhang = tenkhachhang;
        Ngaythangnamsinh = ngaythangnamsinh;
        Diachi = diachi;
        Matkhau = matkhau;
    }

    public int getMakhachhang() {
        return Makhachhang;
    }

    public void setMakhachhang(int makhachhang) {
        Makhachhang = makhachhang;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        Sodienthoai = sodienthoai;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    public String getTenkhachhang() {
        return Tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        Tenkhachhang = tenkhachhang;
    }

    public String getNgaythangnamsinh() {
        return Ngaythangnamsinh;
    }

    public void setNgaythangnamsinh(String ngaythangnamsinh) {
        Ngaythangnamsinh = ngaythangnamsinh;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }
}
