package com.example.udlogistic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.udlogistic.database.FireBaseManage;
import com.example.udlogistic.model.DoanhThu;

import java.util.UUID;


public class frDialog_ThemDTNhanVien extends DialogFragment {
    FireBaseManage fireBaseManage;
    DoanhThu doanhThu;
    public  interface OnInputSelected{
        void setInputUpdate(DoanhThu doanhThu);
        void setInput(DoanhThu doanhThu);

    }
    public  OnInputSelected onInputSelected;
    View view;
    static final String TAG = "frDialog_ThemKhachHang";
    EditText edtTenNhanVien,edtChucVu,edtTongDT;
    Button btnClose,btnSave;

    public frDialog_ThemDTNhanVien(DoanhThu doanhThu) {
        // Required empty public constructor
      this. doanhThu = doanhThu;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment frDialog_ThemKhachHang.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fireBaseManage = new FireBaseManage();
        view =  inflater.inflate(R.layout.fragment_fr_dialog__doanh_thu_nhan_vien, container, false);
        edtTenNhanVien = view.findViewById(R.id.edtTenNhanVien);
        edtChucVu = view.findViewById(R.id.edtChucvu);
        edtTongDT = view.findViewById(R.id.edtTongDT);
        btnClose = view.findViewById(R.id.btnClose);
        btnSave = view.findViewById(R.id.btnSave);
        //Setevent
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (doanhThu!= null)
        {
            edtTenNhanVien.setText(doanhThu.getHoTen());
            edtChucVu.setText(doanhThu.getChucVu());
            edtTongDT.setText(doanhThu.getTongDT());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doanhThu==null)
                {
                    String ma = UUID.randomUUID().toString();;
                    String ten = edtTenNhanVien.getText().toString();
                    String chucvu = edtChucVu.getText().toString();
                    String tongDT = edtTongDT.getText().toString();
                    onInputSelected.setInput(new DoanhThu(ma,ten,chucvu,tongDT));
                    Toast.makeText(view.getContext(),"Đã thêm thành công" , Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();

                }else
                {
                    String ma = doanhThu.getMaNhanVien();
                    String ten = edtTenNhanVien.getText().toString();
                    String chucvu = edtChucVu.getText().toString();
                    String tongDT = edtTongDT.getText().toString();
                    onInputSelected.setInputUpdate(new DoanhThu(ma,ten,chucvu,tongDT));
                    Toast.makeText(view.getContext(),"Đã sửa thành công" , Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onInputSelected = (OnInputSelected)getTargetFragment();
    }
}