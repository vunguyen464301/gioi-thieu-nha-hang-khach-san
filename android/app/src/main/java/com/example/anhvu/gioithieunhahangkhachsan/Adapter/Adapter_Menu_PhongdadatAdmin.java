package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datphong;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.util.ArrayList;

public class Adapter_Menu_PhongdadatAdmin extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Datphong> list;

    public Adapter_Menu_PhongdadatAdmin(Context context, ArrayList<Class_Table_Datphong> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    private class ViewHolder {
        TextView Maphong, Makhachhang,Ghichu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu_phongdadat_admin, null);
            viewHolder.Maphong=convertView.findViewById(R.id.txtMenu_MAP_datphong_Admin);
            viewHolder.Makhachhang=convertView.findViewById(R.id.txtMenu_MAKH_datphong_Admin);
            viewHolder.Ghichu=convertView.findViewById(R.id.txtMenu_GHICHU_datphong_Admin);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Class_Table_Datphong cd=list.get(position);
        viewHolder.Maphong.setText(cd.getMaphong()+"");
        viewHolder.Makhachhang.setText(cd.getMakhachhang()+"");
        viewHolder.Ghichu.setText(cd.getGhichu()+"");


        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }
}
