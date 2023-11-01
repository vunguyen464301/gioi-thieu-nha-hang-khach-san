package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Ban;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Tinnhan;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.util.ArrayList;

public class Adapter_Menu_Tinnhan_Admin extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Tinnhan> list;

    public Adapter_Menu_Tinnhan_Admin(Context context, ArrayList<Class_Table_Tinnhan> list) {
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
        TextView txtTinnhanmember,txtTinnhanAdmin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder viewHolder;
     if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //set trong đây ,nêu là tin nhắn của khách hàng thì nó sẽ hiện thị layout kiểu khách hàng ,nghĩa l
            //nó sẽ hiện thị bên tay trái ,còn tin nhắn của admin sẽ hiện thị bên tay phải
            convertView=inflater.inflate(R.layout.item_tinnhan_admin,null);
            viewHolder.txtTinnhanmember=convertView.findViewById(R.id.txt_item_Tinnhan_Member_Admin);
            viewHolder.txtTinnhanAdmin=convertView.findViewById(R.id.txt_item_Tinnhan_Admin_Admin);
            //convertView = inflater.inflate(R.layout.item_tinnhan_admin, null);
            // viewHolder.txtTinnhan=convertView.findViewById(R.id.txt_item_Tinnhan_Admin);
            convertView.setTag(viewHolder);
        } else
            viewHolder =  (ViewHolder) convertView.getTag();

        Class_Table_Tinnhan cd =list.get(position);
        if(cd.getMakhachhang()==33){
            //viewHolder.txtTinnhanAdmin.setText(cd.getMakhachhang()+" :"+cd.getNoidung());
            viewHolder.txtTinnhanAdmin.setText(cd.getNoidung());
            viewHolder.txtTinnhanmember.setVisibility(View.INVISIBLE);
        }else {
            //viewHolder.txtTinnhanmember.setText(cd.getMakhachhang() + " :" + cd.getNoidung());
            viewHolder.txtTinnhanmember.setText(cd.getNoidung());
            viewHolder.txtTinnhanAdmin.setVisibility(View.INVISIBLE);
        }




        return convertView;
    }
}
