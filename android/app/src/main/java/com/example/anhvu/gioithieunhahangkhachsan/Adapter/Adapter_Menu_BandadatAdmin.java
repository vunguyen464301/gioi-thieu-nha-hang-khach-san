package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datban;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.util.ArrayList;

public class Adapter_Menu_BandadatAdmin extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Datban> list;

    public Adapter_Menu_BandadatAdmin(Context context, ArrayList<Class_Table_Datban> list) {
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
        TextView Maban, Makhachhang,Ghichu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu_bandadat_admin, null);
            viewHolder.Maban=convertView.findViewById(R.id.txtMenu_MAB_datban_Admin);
            viewHolder.Makhachhang=convertView.findViewById(R.id.txtMenu_MAKH_datban_Admin);
            viewHolder.Ghichu=convertView.findViewById(R.id.txtMenu_GHICHU_datban_Admin);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Class_Table_Datban cd=list.get(position);
        viewHolder.Maban.setText(cd.getMaban()+"");
        viewHolder.Makhachhang.setText(cd.getMakhachhang()+"");
        viewHolder.Ghichu.setText(cd.getGhichu()+"");


        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }
}
