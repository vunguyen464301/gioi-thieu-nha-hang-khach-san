package com.example.anhvu.gioithieunhahangkhachsan.Class;

public class Class_Table_Dichvu {
    private int madichvu;
    private String hinhchung;
    private String tendichvu;
    private String chitiet;

    public Class_Table_Dichvu(int madichvu, String hinhchung, String tendichvu, String chitiet) {
        this.madichvu = madichvu;
        this.hinhchung = hinhchung;
        this.tendichvu = tendichvu;
        this.chitiet = chitiet;
    }

    public int getMadichvu() {
        return madichvu;
    }

    public void setMadichvu(int madichvu) {
        this.madichvu = madichvu;
    }

    public String getHinhchung() {
        return hinhchung;
    }

    public void setHinhchung(String hinhchung) {
        this.hinhchung = hinhchung;
    }

    public String getTendichvu() {
        return tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        this.tendichvu = tendichvu;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }
}
