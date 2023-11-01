package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Tinnhan;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class Adapter_Menu_Tinnhan_gridview extends BaseAdapter {
    Context context;
    ArrayList<Integer> list;
    ArrayList<Class_Table_Tinnhan> arrayListTinnhan;

    public Adapter_Menu_Tinnhan_gridview(Context context, ArrayList<Integer> list) {
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
        TextView txtTinnhan,txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_girdview_messenger, null);
            viewHolder.txtTinnhan=convertView.findViewById(R.id.txt1_item_gridview_admin);
            viewHolder.txtName=convertView.findViewById(R.id.txt2_item_gridview_admin);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

       // Integer cd=list.get(position);
        //viewHolder.txtTinnhan.setText("1");
        viewHolder.txtName.setText(list.get(position)+"");
        //ở đây makh=MAHOPTHOAI
        laycsdlTinnhanWhereMAKH(list.get(position),viewHolder);
        return convertView;
    }
    private void laycsdlTinnhanWhereMAKH(int Makh, final ViewHolder viewHolder){
        final int[] dem = {0};
        String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_where.php?MAHOPTHOAI="+Makh;
        arrayListTinnhan=new ArrayList<Class_Table_Tinnhan>();
        RequestQueue requestQueue = new Volley().newRequestQueue(context.getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                    for (int i = jsonArray.length()-1; i >=0; i--) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(jsonObject.getInt("MAKHACHHANG")==33){
                               //Toast.makeText(context.getApplicationContext(),dem[0]+"",Toast.LENGTH_SHORT).show();
                                viewHolder.txtTinnhan.setText(dem[0]+"");
                                break;
                            }
                            dem[0]++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context.getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}
