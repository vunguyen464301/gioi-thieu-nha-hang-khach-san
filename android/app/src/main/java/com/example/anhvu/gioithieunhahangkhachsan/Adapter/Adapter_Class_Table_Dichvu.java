package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Dichvu;
import com.example.anhvu.gioithieunhahangkhachsan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Adapter_Class_Table_Dichvu extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Dichvu> list;

    public Adapter_Class_Table_Dichvu(Context context, ArrayList<Class_Table_Dichvu> list) {
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
        TextView Maloai, tenloai;
        ImageView hinhchung;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_fragment_3, null);

            viewHolder.hinhchung = convertView.findViewById(R.id.img_listview_fragment_3);
            viewHolder.Maloai = convertView.findViewById(R.id.txtMadichvu_fragment_3);
            viewHolder.tenloai = convertView.findViewById(R.id.txtTendichvu_fragment_3);
            //phải settag converview lại ,không thì khi kéo lên kéo xuống sẽ bị bung lỗi
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        Class_Table_Dichvu cd = list.get(position);

        //viewHolder.hinhchung.setImageResource(R.drawable.no_image);

        String url = urlpublic+"siteNhaHangvaKhachSan/hinhanh/" + cd.getHinhchung();
        Picasso.with(context).load(url).into(viewHolder.hinhchung);

        viewHolder.Maloai.setText(cd.getMadichvu() + "");
        viewHolder.tenloai.setText(cd.getTendichvu() + "");

        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }
}
