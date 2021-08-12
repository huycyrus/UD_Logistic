package com.example.udlogistic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.udlogistic.R;
import com.example.udlogistic.database.FireBaseManage;
import com.example.udlogistic.frDialog_ThemDTNhanVien;
import com.example.udlogistic.fr_DTNhanVien;
import com.example.udlogistic.model.DoanhThu;

import java.util.ArrayList;

public class Adapter_LvDoanhThuNhanVien extends ArrayAdapter implements Filterable {
    Context context; int resource;
    ArrayList<DoanhThu> doanhThus = new ArrayList<>();
    ArrayList<DoanhThu> source = new ArrayList<>();
    FragmentManager fragmentManager;
    fr_DTNhanVien fr_dtNhanVien;
    FireBaseManage fireBaseManage = new FireBaseManage();
    public Adapter_LvDoanhThuNhanVien(@NonNull Context context, int resource, @NonNull ArrayList<DoanhThu>doanhThus) {
        super(context, resource, doanhThus);
        this.context = context;
        this.resource = resource;
        this.doanhThus = doanhThus;
        this.source = doanhThus;
    }
    public  void setFragmentManage( FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }
    public  void setfr_DTNhanVien( fr_DTNhanVien fr_dtNhanVien)
    {
        this.fr_dtNhanVien = fr_dtNhanVien;
    }
    @Override
    public int getCount() {
        return doanhThus.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    doanhThus = source;
                } else {
                    ArrayList<DoanhThu> list = new ArrayList<>();
                    for (DoanhThu doanhThu : source) {
                        if (doanhThu.getHoTen().toLowerCase().contains(strSearch.toLowerCase())||
                                doanhThu.getChucVu().toLowerCase().contains(strSearch.toLowerCase())||
                                doanhThu.getTongDT().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(doanhThu);
                        }
                    }
                    doanhThus = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = doanhThus;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                doanhThus = (ArrayList<DoanhThu>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.lvkhachhang_item,null);
        //Nạp layout
        TextView txtSTT = view.findViewById(R.id.txtSTT);
        TextView txtHoTen = view.findViewById(R.id.txtHoTen);
        TextView txtChucVu = view.findViewById(R.id.txtChucVu);
        TextView txtTongDT = view.findViewById(R.id.txtTongDT);
        Button btnSua = view.findViewById(R.id.btnSua);

        DoanhThu doanhThu = doanhThus.get(position);
        //Nạp dữ liệu
        if (doanhThu == null) {
            return null;
        }
        txtSTT.setText((position+1)+"");
        txtHoTen.setText((doanhThu.getHoTen()));
        txtChucVu.setText(doanhThu.getChucVu());
        txtTongDT.setText(doanhThu.getTongDT());
        //Sự kiện
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khởi tạo popup menu
                PopupMenu popupMenu = new PopupMenu(v.getContext(), btnSua);
                popupMenu.inflate(R.menu.lv_edit_button);
                popupMenu.show();
                //Set Sự kiện khi nhấn vào 1 item trong pop up menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item,position);
                    }
                });
            }
        });
        return view;

    }
    private boolean menuItemClicked(MenuItem item, int position) {
        FragmentActivity activity = (FragmentActivity)(context);
        FragmentManager fm = activity.getSupportFragmentManager();
        android.app.FragmentManager fm2 = activity.getFragmentManager();

        switch (item.getItemId()) {
            case R.id.menuItem_Edit:
                frDialog_ThemDTNhanVien frDialog_themDTNhanVien = new frDialog_ThemDTNhanVien(doanhThus.get(position));

                frDialog_themDTNhanVien.setTargetFragment(fr_dtNhanVien,1);
                frDialog_themDTNhanVien.show(fragmentManager,"frDialog_ThemDTNhanVien");
                break;
            case R.id.menuItem_Delete:
                //Tạo message box ? Bạn có muốn xóa
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                //set Text và sự kiện cho nút accept
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        fireBaseManage.deleteDoanhThu(doanhThus.get(position));
                        dialog.dismiss();
                    }
                });
                //set Text và sự kiện cho nút reject
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                //Hiển thị dialog
                alert.show();
                break;
        }
        return true;
    }
}
