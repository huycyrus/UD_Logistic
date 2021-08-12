package com.example.udlogistic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.udlogistic.adapter.Adapter_LvDoanhThuNhanVien;
import com.example.udlogistic.database.FireBaseManage;
import com.example.udlogistic.model.DoanhThu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fr_DTNhanVien extends Fragment implements frDialog_ThemDTNhanVien.OnInputSelected {

    FireBaseManage fireBaseManage;
    static final String TAG = "fr_DTNhanVien";
    SearchView searchView;
    ListView lvDanhSach;
    View view;
    TextView txtHoTen;
    Adapter_LvDoanhThuNhanVien adapter_lvDoanhThuNhanVien;
    ArrayList<DoanhThu>doanhThus = new ArrayList<DoanhThu>();
    Button btnThemDoanhThu;
    public fr_DTNhanVien() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setControl() {
        lvDanhSach = view.findViewById(R.id.lvDanhSachDT);
        btnThemDoanhThu = view.findViewById(R.id.btnThemKhachHang);
        txtHoTen = view.findViewById(R.id.txtHoTen);
        searchView = view.findViewById(R.id.searchView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fr__doanh_thu_nhan_vien, container, false);
        fireBaseManage = new FireBaseManage();
        setControl();
        loadData();
        setEvent();
        return view;
    }

    private void loadData() {

       fireBaseManage.childDoanhThu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doanhThus.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DoanhThu doanhThu = postSnapshot.getValue(DoanhThu.class);
                    doanhThus.add(doanhThu);
                }
                adapter_lvDoanhThuNhanVien.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setEvent() {
        adapter_lvDoanhThuNhanVien = new Adapter_LvDoanhThuNhanVien(view.getContext(),R.layout.lvdoanhthu_item,doanhThus);
        adapter_lvDoanhThuNhanVien.setFragmentManage(getFragmentManager());
        adapter_lvDoanhThuNhanVien.setfr_DTNhanVien(fr_DTNhanVien.this);

        lvDanhSach.setAdapter(adapter_lvDoanhThuNhanVien);
        btnThemDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frDialog_ThemDTNhanVien frDialog_themDTNhanVien  = new frDialog_ThemDTNhanVien(null);
                if (getFragmentManager() != null) {
                    frDialog_themDTNhanVien.setTargetFragment(fr_DTNhanVien.this,1);
                    frDialog_themDTNhanVien.show(getFragmentManager(),"frDialog_ThemDTNhanVien");
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter_lvDoanhThuNhanVien.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_lvDoanhThuNhanVien.getFilter().filter(newText);
                return true;
            }
        });
    }


    @Override
    public void setInput(DoanhThu doanhThu) {
        fireBaseManage.writeDoanhThu(doanhThu);
    }

    @Override
    public void setInputUpdate(DoanhThu doanhThu) { ;
                fireBaseManage.writeDoanhThu(doanhThu);
        }
    }



