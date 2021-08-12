package com.example.udlogistic.model;

public class DoanhThu {
    String maNhanVien ,hoTen,chucVu,tongDT;

    public DoanhThu(String maNhanVien, String hoTen, String chucVu, String tongDT) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.tongDT = tongDT;
    }

    public DoanhThu() {
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTongDT() {
        return tongDT;
    }

    public void setTongDT(String tongDT) {
        this.tongDT = tongDT;
    }
}
