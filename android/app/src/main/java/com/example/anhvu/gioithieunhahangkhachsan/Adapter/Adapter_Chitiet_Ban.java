package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Ban;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datban;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class Adapter_Chitiet_Ban extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Ban> list;
    ArrayList<Class_Table_Datban> arrayListDatban;
    Activity parentActivity;




    public Adapter_Chitiet_Ban(Context context, ArrayList<Class_Table_Ban> list,Activity parentActivity) {
        this.context = context;
        this.list = list;
        this.parentActivity=parentActivity;
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
        Button btnTuychon;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final String maban, tinhtrang;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_fragment_2_chitiet, null);

            viewHolder.Maban=convertView.findViewById(R.id.txtMaban_fragment_2_chitiet);
            viewHolder.Mota = convertView.findViewById(R.id.txtMota_fragment_2_chitiet);
            viewHolder.Maloaiban = convertView.findViewById(R.id.txtMaloaiban_fragment_2_chitiet);
            viewHolder.Tinhtrang=convertView.findViewById(R.id.txtTinhtrang_fragment_2_chitiet);
            viewHolder.btnTuychon=convertView.findViewById(R.id.btnTuychon_fragment_2_chitiet);
            //phải settag converview lại ,không thì khi kéo lên kéo xuống sẽ bị bung lỗi
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Class_Table_Ban cd = list.get(position);


        viewHolder.Maban.setText(cd.getMaban()+"");
        viewHolder.Mota.setText(cd.getMota()+"");
        viewHolder.Maloaiban.setText(cd.getMaloaiban()+"");
        viewHolder.Tinhtrang.setText(cd.getTinhtrang()+"");


        if(cd.getTinhtrang().equals("Đã đặt")){
            viewHolder.btnTuychon.setText("Hủy");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_16));
            }
        }else{
            viewHolder.btnTuychon.setText("Đặt ngay");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_8));
            }

        }




        maban = cd.getMaban();
        viewHolder.btnTuychon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //khi người dùng click vào đây thì khởi tạo lại đếm ==2
                //tránh tình trạng để bên ngoài ,khi bấm lần thứ n thì nó lại chạy tiếp ko phải bắt đầu là số 2
                //vì thế set trong đây

                if(khachhangtamthoi.size()>0){
                    hamtinhtoan(maban,viewHolder,position);
                }else{
                    //Toast.makeText(context.getApplicationContext(),"Tính năng này không dành cho khách !",Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Tính năng này không dành cho khách !");
                }
           }
        });

        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }
    private void hamtinhtoan(String maban,ViewHolder viewHolder,int position){
        //phải là tạo như vậy vì khi điều kiện như vậy phải để trong chuỗi mới tìm kiếm được
        //vd:http://localhost/siteNhaHangvaKhachSan/read_datban_where.php?MABAN="RA01"
        String str=maban;
        //String ma ="\""+str+"\"";
        String url = urlpublic+ "siteNhaHangvaKhachSan/read_datban_where.php?MABAN="+str;
       //Toast.makeText(context.getApplicationContext(),maban,Toast.LENGTH_SHORT).show();
         laycsdlDatbanonMySQLWHERE(url,maban,viewHolder,position);
    }
    private void setView(ViewHolder viewHolder, int position, String maban){
        final String s1="Đã đặt";
        final String s2="Chưa đặt";
        final Class_Table_Ban cd = list.get(position);
        //ý là kiểm tra trong arraylist của mảng có phải là đặt hay chưa
        //nếu chưa đặt thì đổi lại là đặt và kiểm tra trong csdl localhost
        //nếu đặt rồi thì kiểm tra ,bàn đó có trùng với với makh này hay không ,nếu trùng thì được phép hủy ,
        //ý đổi chưa đặt thành đã đặt là vì khi đó mới set click vào thì phải đổi ,đổi cả  phần tử arraylist

        if(cd.getTinhtrang().equals("Chưa đặt")) {
            list.set(position, new Class_Table_Ban(cd.getMaban()
                    , cd.getMota(), cd.getMaloaiban(),s1));
            viewHolder.Tinhtrang.setText(s1);
            //phải ngược lại với trạng thái để người dùng tùy chọn
            viewHolder.btnTuychon.setText("Hủy");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_16));
            }

            String url = urlpublic+"siteNhaHangvaKhachSan/update_ban.php";
            //s1 hoặc s2 là tình trạng để insert vào bảng csdl
            capnhatTableBan(url, maban,s1);
            String urlinsert=urlpublic+"siteNhaHangvaKhachSan/insert_datban.php";
            int makh =  khachhangtamthoi.get(0).getMakhachhang();
            String ghichu = "Đã duyệt";
            insertDatavaotableDATBAN(urlinsert,maban,makh,ghichu);

        }else{
            list.set(position, new Class_Table_Ban(cd.getMaban()
                    , cd.getMota(), cd.getMaloaiban(),s2));
            viewHolder.Tinhtrang.setText(s2);
            viewHolder.btnTuychon.setText("Đặt ngay");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_8));
            }
            String url = urlpublic+"siteNhaHangvaKhachSan/update_ban.php";
            //s1 hoặc s2 là tình trạng để insert vào bảng csdl
            capnhatTableBan(url, maban,s2);
            String urlxoa=urlpublic+"siteNhaHangvaKhachSan/delete_datban.php";
            deleteTableDatban(urlxoa,maban);
        }

    }
    //tham số truyền vào không cần position vì là đã ánh xạ và get nó ở trên nên chỉ cần gọi tên biến là ra
    private void capnhatTableBan(String url, final String maban, final String tinhtrang){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("success")){
                            Toast.makeText(context.getApplicationContext(),"Cập nhật thành công !",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context.getApplicationContext(),"Cập nhật thất bại !",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context.getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("maban",maban);
                params.put("tinhtrang",tinhtrang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void insertDatavaotableDATBAN(String url, final String maban, final int makh, final String ghichu){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                if(s.equals("success")){
                    //Toast.makeText(context.getApplicationContext(),"Đặt thành công",Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Đặt thành công");
                }else{
                    //Toast.makeText(context.getApplicationContext(),"Đặt thất bại",Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Đặt thất bại");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context.getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String ,String> params =new HashMap<>();
                params.put("MABAN",maban);
                params.put("MAKHACHHANG",makh+"");
                params.put("GHICHU",ghichu);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void deleteTableDatban(String url, final String maban){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                   // Toast.makeText(context.getApplicationContext(), "Hủy thành công", Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Hủy thành công");
                }else{
                    //Toast.makeText(context.getApplicationContext(), "Hủy thất bại", Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Hủy thất bại");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context.getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params=new HashMap<>();
                params.put("MABAN",maban);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void laycsdlDatbanonMySQLWHERE(String url, final String maban, final ViewHolder viewHolder, final int position)
    {
        arrayListDatban=new ArrayList<Class_Table_Datban>();
        RequestQueue requestQueue = new Volley().newRequestQueue(context.getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Datban ban;
                        ban = new Class_Table_Datban(jsonObject.getString("MABAN"),
                                jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("GHICHU"));

                        arrayListDatban.add(ban);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(arrayListDatban.size()>0) {
                    //get 0 là do 1 phòng chỉ được 1 khách đặt
                    Class_Table_Datban db = arrayListDatban.get(0);
                    int makhhtrongDatban;
                    makhhtrongDatban = db.getMakhachhang();
                    int makh=khachhangtamthoi.get(0).getMakhachhang();
                    //1 bàn chỉ cần một người đặt nên là get vị trí thứ 0
                    if (makh == makhhtrongDatban) {
                        //Toast.makeText(context.getApplicationContext(),"hUy ban", Toast.LENGTH_SHORT).show();
                        Notification_thongbaoHuy(viewHolder,position,maban);

                    }else{
                        //Toast.makeText(context.getApplicationContext(), "Đặt bàn thất bại do có người khác đặt !", Toast.LENGTH_SHORT).show();
                        Notification_thongbao("Hủy bàn thất bại do có người khác đặt !");
                    }
                }else{
                    Toast.makeText(context.getApplicationContext(), "Đặt phòng thành công do chưa có ai đặt !", Toast.LENGTH_SHORT).show();
                    //Notification_thongbao("Đặt phòng thành công do chưa có ai đặt !");
                    setView(viewHolder,position,maban);
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
    //thông báo xác nhận hủy thì chỉ có 1
    private void Notification_thongbaoHuy(final ViewHolder viewHolder, final int position, final String maban){
        //vì là chỉ có 1 activity nên không cần quá nhiều tham số làm gì
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn muốn hủy ?");
        builder.setNegativeButton("Tôi đồng ý ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context.getApplicationContext(), "Đang hủy !", Toast.LENGTH_SHORT).show();
                setView(viewHolder,position,maban);
            }
        });
        builder.setPositiveButton("Tôi từ chối ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void Notification_thongbao(String str){
        //vì là chỉ có 1 activity nên không cần quá nhiều tham số làm gì
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Thông báo !");
        builder.setMessage(str);
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

