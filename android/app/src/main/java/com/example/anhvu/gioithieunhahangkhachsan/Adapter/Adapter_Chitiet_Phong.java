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
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datphong;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Adapter_Chitiet_Phong extends BaseAdapter {
    Context context;
    ArrayList<Class_Table_Phong> list;

    ArrayList<Class_Table_Datphong> arrayListDatphong;
    Activity parentActivity;
    //makh trong khachhangtamthoi

    //makh trong datphong



    public Adapter_Chitiet_Phong(Context context, ArrayList<Class_Table_Phong> list,Activity parentActivity) {
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
        TextView Maphong, Mota, Maloaiphong,Dongia1gio, Dongiangay, Dongiadem, Dongiangaydem,Tiennghi, Tinhtrang;
        ImageView Hinhanh;
        Button btnTuychon;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final String maphong, tinhtrang;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_fragment_1_chitiet, null);

            viewHolder.Maphong = convertView.findViewById(R.id.txtMaphong_fragment_1_chitiet);
            viewHolder.Mota = convertView.findViewById(R.id.txtMota_fragment_1_chitiet);
            viewHolder.Hinhanh = convertView.findViewById(R.id.img_listview_fragment_1_chitiet);
            viewHolder.Maloaiphong = convertView.findViewById(R.id.txtMaloaiphong_fragment_1_chitiet);
            viewHolder.Dongia1gio=convertView.findViewById(R.id.txtDongia1gio_fragment_1_chitiet);
            viewHolder.Dongiangay = convertView.findViewById(R.id.txtDongiangay_fragment_1_chitiet);
            viewHolder.Dongiadem = convertView.findViewById(R.id.txtDongiadem_fragment_1_chitiet);
            viewHolder.Dongiangaydem = convertView.findViewById(R.id.txtDongiangaydem_fragment_1_chitiet);
            viewHolder.Tiennghi=convertView.findViewById(R.id.txtTiennghi_fragment_1_chitiet);
            viewHolder.Tinhtrang = convertView.findViewById(R.id.txtTinhtrang_fragment_1_chitiet);
            viewHolder.btnTuychon = convertView.findViewById(R.id.btnTuychon_fragment_1_chitiet);
            //phải settag converview lại ,không thì khi kéo lên kéo xuống sẽ bị bung lỗi
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();


        final Class_Table_Phong cd = list.get(position);


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


        maphong = cd.getMaphong();




        viewHolder.btnTuychon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setView(viewHolder,position,maphong);

                //hamtinhtoan(maphong,dem);
                if(khachhangtamthoi.size()>0){
                    hamtinhtoan(maphong,viewHolder,position);
                }else{
                    Toast.makeText(context.getApplicationContext(),"Tính năng này không dành cho khách !",Toast.LENGTH_SHORT).show();
                }


            }
        });

        Animation anim= AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.phongtomotxiu);
        convertView.startAnimation(anim);
        return convertView;
    }

    private void hamtinhtoan(String maphong,ViewHolder viewHolder,int position){
        //phải là tạo như vậy vì khi điều kiện như vậy phải để trong chuỗi mới tìm kiếm được
        //vd:http://localhost/siteNhaHangvaKhachSan/read_datban_where.php?MABAN="RA01"
        String str=maphong;
        //String ma ="\""+str+"\"";
        String url = urlpublic+ "siteNhaHangvaKhachSan/read_datphong_where.php?MAPHONG="+str;
        laycsdlDatphongonMySQLWHERE(url,maphong,viewHolder,position);
        //handlerTime.postDelayed(runnable,0);


    }

    private void setView(ViewHolder viewHolder, int position, String maphong) {
        final String s1="Đã đặt";
        final String s2="Chưa đặt";
        final Class_Table_Phong cd = list.get(position);

        //ý là kiểm tra trong arraylist của mảng có phải là đặt hay chưa
        //nếu chưa đặt thì đổi lại là đặt và kiểm tra trong csdl localhost
        //nếu đặt rồi thì kiểm tra ,bàn đó có trùng với với makh này hay không ,nếu trùng thì được phép hủy ,
        //ý đổi chưa đặt thành đã đặt là vì khi đó mới set click vào thì phải đổi ,đổi cả  phần tử arraylist

        if(cd.getTinhtrang().equals("Chưa đặt")) {
            list.set(position, new Class_Table_Phong(cd.getMaphong()
                    , cd.getMota(), cd.getHinhanh(), cd.getMaloaiphong(),cd.getGia1gio(), cd.getDongiangay()
                    , cd.getDongiadem(), cd.getDongiangaydem(),cd.getTiennghi(), s1));
            viewHolder.Tinhtrang.setText(s1);

            viewHolder.btnTuychon.setText("Hủy");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_16));
            }

            String url = urlpublic+"siteNhaHangvaKhachSan/update_phong.php";
            //s1 hoặc s2 là tình trạng để insert vào bảng csdl
            capnhatTablePhong(url, maphong,s1);
            String urlinsert=urlpublic+"siteNhaHangvaKhachSan/insert_datphong.php";
            int makh =  khachhangtamthoi.get(0).getMakhachhang();
            String ghichu = "Đã duyệt";
            insertDatavaotableDATPHONG(urlinsert,maphong,makh,ghichu);
        }else{
            list.set(position, new Class_Table_Phong(cd.getMaphong()
                    , cd.getMota(), cd.getHinhanh(), cd.getMaloaiphong(),cd.getGia1gio(), cd.getDongiangay()
                    , cd.getDongiadem(), cd.getDongiangaydem(),cd.getTiennghi(), s2));
            viewHolder.Tinhtrang.setText(s2);

            viewHolder.btnTuychon.setText("Đặt ngay");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.btnTuychon.setBackground(this.parentActivity.getResources().getDrawable(R.drawable.homepage_8));
            }

            String url = urlpublic+"siteNhaHangvaKhachSan/update_phong.php";
            //s1 hoặc s2 là tình trạng để insert vào bảng csdl
            capnhatTablePhong(url, maphong,s2);
            String urlxoa=urlpublic+"siteNhaHangvaKhachSan/delete_datphong.php";
            deleteTableDatphong(urlxoa,maphong);
        }




    }
    private void capnhatTablePhong(String url, final String maphong, final String tinhtrang) {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("success")) {
                            Toast.makeText(context.getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context.getApplicationContext(), "Loi: " + volleyError.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maphong", maphong);
                params.put("tinhtrang", tinhtrang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void laycsdlDatphongonMySQLWHERE(String url, final String maphong, final ViewHolder viewHolder, final int position)
    {
        arrayListDatphong=new ArrayList<Class_Table_Datphong>();
        RequestQueue requestQueue = new Volley().newRequestQueue(context.getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Datphong phong;
                        phong = new Class_Table_Datphong(jsonObject.getString("MAPHONG"),
                                jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("GHICHU"));

                        arrayListDatphong.add(phong);
                        //  Toast.makeText(context.getApplicationContext(),ban.getMaban()+" "+ban.getMakhachhang(),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //lấy xong rồi duyệt kiểm tra
                if(arrayListDatphong.size()>0) {
                    //get 0 là do 1 phòng chỉ được 1 khách đặt
                    Class_Table_Datphong db = arrayListDatphong.get(0);
                    int makhhtrongDatphong;
                    makhhtrongDatphong = db.getMakhachhang();
                    int makh=khachhangtamthoi.get(0).getMakhachhang();
                    //1 bàn chỉ cần một người đặt nên là get vị trí thứ 0
                    if (makh == makhhtrongDatphong) {
                       Notification_thongbaoHuy(viewHolder,position,maphong);
                    }else{
                        //Toast.makeText(context.getApplicationContext(), "Dat that bai do co nguoi khac dat !", Toast.LENGTH_SHORT).show();
                        Notification_thongbao("Phòng này đã có người đặt !");
                    }
                }else{
                    Toast.makeText(context.getApplicationContext(), "Đặt phòng thành công do chưa có ai đặt !", Toast.LENGTH_SHORT).show();
                    setView(viewHolder,position,maphong);
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

    private void deleteTableDatphong(String url, final String maphong){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String kq) {
                if(kq.equals("success")){
                   //  Toast.makeText(context.getApplicationContext(), "delete thanh cong", Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Hủy thành công");
                }else{
                    //Toast.makeText(context.getApplicationContext(), "Delete that bai: "+kq.toString()+"", Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Hủy thất bại ");
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
                params.put("MAPHONG",maphong);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void insertDatavaotableDATPHONG(String url, final String maphong, final int makh, final String ghichu){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context.getApplicationContext()," hien thi : " + s.toString(), Toast.LENGTH_SHORT).show();
                if(s.equals("success")){
                    //Toast.makeText(context.getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    Notification_thongbao("Đặt thành công");
                }else{
                    //Toast.makeText(context.getApplicationContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
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
                params.put("MAPHONG",maphong+"");
                params.put("MAKHACHHANG",makh+"");
                params.put("GHICHU",ghichu);
                return params;

            }
            //            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String ,String> params =new HashMap<>();
//                params.put("MAPHONG",maphong+"");
//                params.put("MAKHACHHANG",makh+"");
//                params.put("GHICHU",ghichu);
//
//                return params;
//
 //           }
        };


        requestQueue.add(stringRequest);
    }
    private void Notification_thongbaoHuy(final ViewHolder viewHolder, final int position, final String maphong){
        //vì là chỉ có 1 activity nên không cần quá nhiều tham số làm gì
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn muốn hủy ?");
        builder.setNegativeButton("Tôi đồng ý ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context.getApplicationContext(), "Đang hủy", Toast.LENGTH_SHORT).show();
                //Notification_thongbao("Đặt thành công");
                setView(viewHolder,position,maphong);
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

/*

   if(arrayListDatphong.size()>0) {
                    Class_Table_Datphong db = arrayListDatphong.get(0);
                    int makhhtrongDatphong;
                    makhhtrongDatphong = db.getMakhachhang();
                    //1 bàn chỉ cần một người đặt nên là get vị trí thứ 0
                    if (makhTamthoi == makhhtrongDatphong) {
                        Toast.makeText(context.getApplicationContext(), "Dat thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
 */