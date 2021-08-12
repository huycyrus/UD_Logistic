package com.example.udlogistic.database;

import com.example.udlogistic.model.DoanhThu;
import com.example.udlogistic.model.NhanVien;
import com.example.udlogistic.model.PhongBan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseManage {
    public FirebaseDatabase database;
    public DatabaseReference childDoanhThu;
    public DatabaseReference childPhongBan;
    public DatabaseReference childNhanVien;
    public FireBaseManage() {
        database = FirebaseDatabase.getInstance("https://ud-logistic-e200c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        childDoanhThu = database.getReference("DoanhThu");
        childPhongBan = database.getReference("PhongBan");
        childNhanVien = database.getReference("NhanVien");
    }
    public DatabaseReference getRootReference()
    {
        return database.getReference();
    }
    public void writeNhanVien(NhanVien nhanVien) {
        childNhanVien.child(nhanVien.getMaNhanVien()).setValue(nhanVien);
    }
    public void deleteNhanVien(NhanVien nhanVien) {
        childNhanVien.child(nhanVien.getMaNhanVien()).removeValue();
    }
    public void writeDoanhThu(DoanhThu doanhThu)
    {
        childDoanhThu.child(doanhThu.getMaNhanVien()).setValue(doanhThu);
    }
    public void deleteDoanhThu(DoanhThu doanhThu)
    {
        childDoanhThu.child(doanhThu.getMaNhanVien()).removeValue();
    }
    public void delePhongBan(PhongBan phongBan)
    {
        childPhongBan.child(phongBan.getMaPhong()).removeValue();
    }
    public void writePhongBan(PhongBan phongBan)
    {
        childPhongBan.child(phongBan.getMaPhong()).setValue(phongBan);
    }

}
