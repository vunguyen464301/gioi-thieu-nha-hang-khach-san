package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Ban;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Adapter_Menu_Bandadat extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Ban> list;

    public Adapter_Menu_Bandadat(Context context, ArrayList<Class_Table_Ban> list) {
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
        TextView Maban,Mota,Maloaiban,Tinhtrang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final String maban, tinhtrang;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu_bandadat, null);

            viewHolder.Maban=convertView.findViewById(R.id.txtMaban_Menu_Bandadat);
            viewHolder.Mota = convertView.findViewById(R.id.txtMota_Menu_Bandadat);
            viewHolder.Maloaiban = convertView.findViewById(R.id.txtMaloaiban_Menu_Bandadat);
            viewHolder.Tinhtrang=convertView.findViewById(R.id.txtTinhtrang_Menu_Bandadat);
            //phải settag converview lại ,không thì khi kéo lên kéo xuống sẽ bị bung lỗi
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Class_Table_Ban cd = list.get(position);


        viewHolder.Maban.setText(cd.getMaban()+"");
        viewHolder.Mota.setText(cd.getMota()+"");
        viewHolder.Maloaiban.setText(cd.getMaloaiban()+"");
        viewHolder.Tinhtrang.setText(cd.getTinhtrang()+"");

        maban = cd.getMaban();

        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);



        return convertView;
    }


}
