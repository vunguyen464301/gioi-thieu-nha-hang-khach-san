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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Adapter_Menu_Phongdadat extends BaseAdapter{
    //dùng chung class Class_Table_Phong
    Context context;
    ArrayList<Class_Table_Phong> list;

    public Adapter_Menu_Phongdadat(Context context, ArrayList<Class_Table_Phong> list) {
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
        TextView Maphong, Mota, Maloaiphong,Dongia1gio, Dongiangay, Dongiadem, Dongiangaydem,Tiennghi, Tinhtrang;
        ImageView Hinhanh;
        Button btnHuy;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        final String maphong;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu_phongdadat, null);

            viewHolder.Maphong = convertView.findViewById(R.id.txtMaphong_Menu_Phongdadat);
            viewHolder.Mota = convertView.findViewById(R.id.txtMota_Menu_Phongdadat);
            viewHolder.Hinhanh = convertView.findViewById(R.id.img_listview_Menu_Phongdadat);
            viewHolder.Maloaiphong = convertView.findViewById(R.id.txtMaloaiphong_Menu_Phongdadat);
            viewHolder.Dongia1gio=convertView.findViewById(R.id.txtDongia1gio_Menu_Phongdadat);
            viewHolder.Dongiangay = convertView.findViewById(R.id.txtDongiangay_Menu_Phongdadat);
            viewHolder.Dongiadem = convertView.findViewById(R.id.txtDongiadem_Menu_Phongdadat);
            viewHolder.Dongiangaydem = convertView.findViewById(R.id.txtDongiangaydem_Menu_Phongdadat);
            viewHolder.Tiennghi =convertView.findViewById(R.id.txtTiennghi_Menu_Phongdadat);
            viewHolder.Tinhtrang = convertView.findViewById(R.id.txtTinhtrang_Menu_Phongdadat);
           // viewHolder.btnHuy = convertView.findViewById(R.id.btnHuy_Menu_Phongdadat);
            //phải settag converview lại ,không thì khi kéo lên kéo xuống sẽ bị bung lỗi
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();


        Class_Table_Phong cd = list.get(position);


        viewHolder.Maphong.setText(cd.getMaphong() + "");
        viewHolder.Mota.setText(cd.getMota() + "");

        String urlImages =urlpublic+"siteNhaHangvaKhachSan/hinhanh/" + cd.getHinhanh();
        Picasso.with(context).load(urlImages).into(viewHolder.Hinhanh);

        viewHolder.Maloaiphong.setText(cd.getMaloaiphong() + "");
        viewHolder.Dongia1gio.setText(cd.getGia1gio()+"");
        viewHolder.Dongiangay.setText(cd.getDongiangay() + "");
        viewHolder.Dongiadem.setText(cd.getDongiadem() + "");
        viewHolder.Dongiangaydem.setText(cd.getDongiangaydem() + "");
        viewHolder.Tiennghi.setText(cd.getTiennghi()+"");
        viewHolder.Tinhtrang.setText(cd.getTinhtrang() + "");
        //tuỳ chọn thì nên dùng hiện lên chữ tùy chọn

        maphong=cd.getMaphong();
        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }

}
