package com.example.anhvu.gioithieunhahangkhachsan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_Tinnhan_Admin;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_Tinnhan_Member;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Tinnhan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class MenuCHATSupportAdminActivity extends AppCompatActivity {
    ListView listView;
    EditText edtMess;
    ImageButton imgImageButton;
    TextView txtDangsoantin;
    Intent intent;
    ArrayList<Class_Table_Tinnhan> arrayListTinnhan;
    Adapter_Menu_Tinnhan_Admin adapter_menu_tinnhan;
    RelativeLayout re1;
    ProgressBar progressBar1;

    private int MAHOPTHOAI;
    private Boolean tinhtrangAdmin,tinhtrangMember;
    private int dem=3,demthuthapdulieutubanphim=4,demload=5;
    private String bonhotamthuthapdulieutubanphim;

    //thread này dùng để load 5s progresbar
    private Handler handlerload5s=new Handler();
    private Runnable runnableload5s=new Runnable() {
        @Override
        public void run() {
            demload=demload-1;
            handlerload5s.postDelayed(runnableload5s,1000);
            if(demload==0) {
                handlerload5s.removeCallbacks(runnableload5s);

                listView.setVisibility(View.VISIBLE);
                re1.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.INVISIBLE);

                handlerLoadDangsoantin.postDelayed(runnableLoadDangsoantin, 0);
                //thay vì cập nhật chớp liên tục thì cứ 3s thì nó kiểm tra check còn ở trên là check trong mảng liên tục
                handlerLoadCheckDangsoantin.postDelayed(runnableLoadCheckDangsoantin, 3000);

                //b1 kiểm tra
                //b2 soạn tin nhắn thì nó auto gửi dữ liệu lên bảng ,sau khi ngừng soạn 3s thì nó mới ngừng gửi dữ liệu

                //hàm này thu thập dữ liệu liên tục từ bàn phím để gửi dữ liệu lên bảng Đang soạn tin
                //Nếu người dùng không nhập nữa thì sau 3s nó tự động delete trên bảng ,
                //nếu người dùng nhập liên tục thì nó cứ gửi dữ liệu trên bảng sau mỗi 1s
                //trước mắt kiểm tra bảng dangsoantin có tồn tại dữ liệu hay không với makh hiện tại là đầu vào

                //tao 2 ham
                //ham 1 kiểm tra cứ 3s thì kiểm tra 1 lần với lần khởi động đầu tiên là 0ms
                //hàm 2 cứ sau 3s kiểm tra tinhtrangMemmber=false thì là insert Dữ liệu vào với lần khởi động đầu tiên là sau 3s
                handlerThuthapdulieutubanphimlientuc.postDelayed(runnableThuthapdulieutubanphimlientuc, 1000);
            }
        }
    };
    //cái thread này dùng để load hộp thoại ,tất cả tin nhắn của hộp thoại đó
    private Handler handler =new Handler();
    private Runnable runloadMessenger =new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runloadMessenger,1000);
            //cập nhật liên tục ,cứ sau 1s thì nó cập nhật liền
            //makhachhang la mahopthoai ,còn mã admin thì phải dùng makh
            String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_where.php?MAHOPTHOAI="+MAHOPTHOAI;
            laycsdlTinnhanWhereMAKH(url);
        }
    };

    //hàm này kiểm tra bảng có tồn tại dữ liệu hay không để hiện thị lên ,dĩ nhiên là cập nhật liên tục
    private Handler handlerLoadDangsoantin=new Handler();
    private Runnable runnableLoadDangsoantin=new Runnable() {
        @Override
        public void run() {
            //mặc định phải set nó là false sau mỗi lần vô hàm này
            tinhtrangMember=false;
            handlerLoadDangsoantin.postDelayed(runnableLoadDangsoantin,1000);
            //SET MAHOPTHOAI==MAKHACHHANG
            String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_dangsoantin_where.php?MAHOPTHOAI="+MAHOPTHOAI+"&MAKHACHHANG="+MAHOPTHOAI;
            //ở trong hàm này thì set nó là true ,đương nhiên là sau 2 3s thì nó mới cập nhật ngoài giao diện mới lấy dữ liệu đc
            //sau 3s thì check xem có true hay không ,nếu ko thì thôi ,rồi cứ 3s thì nó check 1 lần
            laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoMEMBER(url);

            //nếu mà không có thì chờ 3s sau rồi cho ẩn đi
            //vì là cứ 3s thì nó kiểm tra 1 lần xem có tồn tại dữ liệu trong bảng Dangsoantin hay không
            //vì tranh thủ 3s thì là set 3 lần chữ ,đầu tiên là phải set hiện thị rồi mới set được ...
            //vì là 3 cái này chạy dữ liệu nền liên tục không ngừng nghỉ ,nên là một khi hiện thị thì nó hiện thị tiếp
            //ẩn thì cứ thế ẩn thôi ,chứ không phải là cứ hiện là bắt đầu là 1 . ,mà là hiện thị tiếp tục dữ liệu nền
            dem=dem-1;
            if(dem==2){
                txtDangsoantin.setText("Đang soạn tin.");
            }else if(dem==1){
                txtDangsoantin.setText("Đang soạn tin..");
            }else {
                txtDangsoantin.setText("Đang soạn tin...");
                dem=3;
            }
        }
    };
    //hàm này check kiểm tra bảng có tồn tại dữ liệu không thì ẩn chữ đang soạn tin đi
    private Handler handlerLoadCheckDangsoantin=new Handler();
    private Runnable runnableLoadCheckDangsoantin=new Runnable() {
        @Override
        public void run() {
            handlerLoadCheckDangsoantin.postDelayed(runnableLoadCheckDangsoantin,3000);
            if(tinhtrangMember==false){
                txtDangsoantin.setVisibility(View.INVISIBLE);
            }

        }
    };

    //hàm này thu thập dữ liệu liên tục từ bàn phím để gửi dữ liệu lên bảng Đang soạn tin
    //Nếu người dùng không nhập nữa thì sau 3s nó tự động delete trên bảng ,
    //nếu người dùng nhập liên tục thì nó cứ gửi dữ liệu trên bảng sau mỗi 3s
    //trước mắt kiểm tra bảng dangsoantin có tồn tại dữ liệu hay không
    private Handler handlerThuthapdulieutubanphimlientuc=new Handler();
    private Runnable runnableThuthapdulieutubanphimlientuc =new Runnable() {
        @Override
        public void run() {
            //khái quát thuật toán
            //khi người dùng nhập vào bàn phím là thu thập liên tục cứ mỗi 1s là cập nhật 1 lần
            //khi người dùng ngừng soạn thi ở giây thứ 2 nó sẽ lưu lại vào biến
            //đến giây thứ 0 người dùng ko thay đổi thì sẽ phải hiện thị là dừng
            //khi người dùng nhập thêm 1 kí tự hoặc xóa ,miễn sao khác chuỗi đã lưu ở biến
            //thì nó sẽ cho soạn lại từ đầu hay còn gọi là soạn tiếp
            tinhtrangAdmin=false;
            String soantin=edtMess.getText().toString();
           //  txtDangsoantin.setVisibility(View.VISIBLE);
            handlerThuthapdulieutubanphimlientuc.postDelayed(runnableThuthapdulieutubanphimlientuc,1000);
            if(soantin.length()>0&&demthuthapdulieutubanphim>=0){
                demthuthapdulieutubanphim=demthuthapdulieutubanphim-1;
                if(demthuthapdulieutubanphim==3){
                    bonhotamthuthapdulieutubanphim=String.valueOf(edtMess.getText().toString());
                    //Toast.makeText(getApplicationContext(),"da luu"+bonhotamthuthapdulieutubanphim+"",Toast.LENGTH_SHORT).show();
                }
                if(demthuthapdulieutubanphim==0&& bonhotamthuthapdulieutubanphim.equals(soantin)){
                    // txtDangsoantin.setText("Tạm ngưng");
                    //xóa
                    String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_dangsoantin_where.php?MAHOPTHOAI="+MAHOPTHOAI+"&MAKHACHHANG="+khachhangtamthoi.get(0).getMakhachhang();
                    xoa_laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoADMIN(url);
                    demthuthapdulieutubanphim=-1;
                }else {
                    //txtDangsoantin.setText("vẫn đang soạn");
                    String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_dangsoantin_where.php?MAHOPTHOAI="+MAHOPTHOAI+"&MAKHACHHANG="+khachhangtamthoi.get(0).getMakhachhang();
                    laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoADMIN(url);
                    //thêm vào 3s sau ,kiểm tra
                    //trong đây cũng có 1 thread thuthap du lieu nên phải hủy thread kia đi
                   handlerLoadDangsoantin3000ms.postDelayed(runnableLoadDangsoantin3000ms,3000);

                   handlerThuthapdulieutubanphimlientuc.removeCallbacks(runnableThuthapdulieutubanphimlientuc);

                }
            }else if(demthuthapdulieutubanphim<=-1){
                // txtDangsoantin.setText("vẫn tạm ngưng");
                if(!bonhotamthuthapdulieutubanphim.equals(soantin)){
                    //thêm vào
                    //kiểm tra rồi mới thêm vào
                  //  txtDangsoantin.setText("soạn lại từ đầu");
                    demthuthapdulieutubanphim=4;
                }
            }
            //hàm này để xóa phần từ
            else{
                 //txtDangsoantin.setText("Ngừng soạn");
                //xóa
                String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_dangsoantin_where.php?MAHOPTHOAI="+MAHOPTHOAI+"&MAKHACHHANG="+khachhangtamthoi.get(0).getMakhachhang();
                xoa_laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoADMIN(url);


            }


        }
    };
    private Handler handlerLoadDangsoantin3000ms=new Handler();
    private Runnable runnableLoadDangsoantin3000ms=new Runnable() {
        @Override
        public void run() {
            //nghĩa là bảng không tồn tại dữ liệu đang soạn tin thì thêm vô
            if(tinhtrangAdmin==false){
                String url =urlpublic+"siteNhaHangvaKhachSan/insert_tinnhan_dangsoantin.php";
                insertDatavaotableTINNHAN_DANGSOANTIN(url);
            }
            handlerThuthapdulieutubanphimlientuc.postDelayed(runnableThuthapdulieutubanphimlientuc,1000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chatsupport_admin);
        AnhXa();

        listView.setVisibility(View.INVISIBLE);
        re1.setVisibility(View.INVISIBLE);
        progressBar1.setVisibility(View.VISIBLE);

        handlerload5s.postDelayed(runnableload5s,0);

        handlerThuthapdulieutubanphimlientuc.postDelayed(runnableThuthapdulieutubanphimlientuc,1000);
        handler.postDelayed(runloadMessenger,1000);

        imgImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =urlpublic+"siteNhaHangvaKhachSan/insert_tinnhan.php";
                insertDatavaotableTINNHAN(url);
                //sau khi gửi tin nhắn thành công thì phải xóa chuỗi trong edt để người dùng soạn lại
               // Toast.makeText(getApplicationContext(),MAHOPTHOAI+"",Toast.LENGTH_SHORT).show();
            }
        });


        customActionBar();
    }

    private void AnhXa(){
        listView=findViewById(R.id.listviewChat_Menu_support_admin);
        edtMess=findViewById(R.id.edtmess_Menu_support_admin);
        imgImageButton=findViewById(R.id.imgbtnsend_Menu_support_admin);
        intent=getIntent();
        MAHOPTHOAI=intent.getIntExtra("MAHT",0);
        txtDangsoantin=findViewById(R.id.txtDangsoantin_Menu_support_admin);
        re1=findViewById(R.id.reMenu_support_admin);
        progressBar1=findViewById(R.id.load_progress_Menu_support_admin);

    }
    private void setAdapter(){
        adapter_menu_tinnhan =new Adapter_Menu_Tinnhan_Admin(  this,arrayListTinnhan);
        listView.setAdapter(adapter_menu_tinnhan);
    }

    private void insertDatavaotableTINNHAN(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(),"Gửi thành công",Toast.LENGTH_SHORT).show();
                    //gửi thành công thì phải xóa chuỗi trong edittext
                    edtMess.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Gửi thất bại",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String ,String> params =new HashMap<>();
                //trim cắt khoảng trắng đầu đuôi
                //mã hộp thoại là mã khách hàng luôn ,vì là 1 khách hàng chỉ có 1 mã hộp thoai ,để
                //để vậy cho dễ phân biệt
                String noidung=edtMess.getText().toString();
                params.put("MAHOPTHOAI",MAHOPTHOAI+"");
                //vì là đang mang danh dự admin nên phải lấy mã admin
                params.put("MAKHACHHANG",khachhangtamthoi.get(0).getMakhachhang()+"");
                params.put("NOIDUNG",noidung.trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void laycsdlTinnhanWhereMAKH(String url){
        arrayListTinnhan=new ArrayList<Class_Table_Tinnhan>();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                //duyệt chỉ cho phép nó hiện thị dc 10 trang
                if(jsonArray.length()>=8){
                    //cách từ mảng cuối cùng -8 thì hiển thị 8
                    for (int i = jsonArray.length()-8; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Class_Table_Tinnhan cd = new Class_Table_Tinnhan(
                                    jsonObject.getInt("SOTHUTU"),
                                    jsonObject.getInt("MAHOPTHOAI"),
                                    jsonObject.getInt("MAKHACHHANG"),
                                    jsonObject.getString("NOIDUNG")
                            );
                            arrayListTinnhan.add(cd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Class_Table_Tinnhan cd = new Class_Table_Tinnhan(
                                    jsonObject.getInt("SOTHUTU"),
                                    jsonObject.getInt("MAHOPTHOAI"),
                                    jsonObject.getInt("MAKHACHHANG"),
                                    jsonObject.getString("NOIDUNG")
                            );
                            arrayListTinnhan.add(cd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                setAdapter();
                listView.setSelection(arrayListTinnhan.size()-1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoMEMBER(String url){
        arrayListTinnhan=new ArrayList<Class_Table_Tinnhan>();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getInt("TINHTRANG")==1){
                            txtDangsoantin.setVisibility(View.VISIBLE);
                            tinhtrangMember=true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        requestQueue.add(jsonArrayRequest);
    }


    private void laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoADMIN(String url){
        arrayListTinnhan=new ArrayList<Class_Table_Tinnhan>();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getInt("TINHTRANG")==1){
                            tinhtrangAdmin=true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        requestQueue.add(jsonArrayRequest);
    }
    private void insertDatavaotableTINNHAN_DANGSOANTIN(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                     Toast.makeText(getApplicationContext(),"Đang soạn tin",Toast.LENGTH_SHORT).show();

                }else{
                     Toast.makeText(getApplicationContext(),"Đang soạn thất bại",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String ,String> params =new HashMap<>();
                //trim cắt khoảng trắng đầu đuôi
                //mã hộp thoại là mã khách hàng luôn ,vì là 1 khách hàng chỉ có 1 mã hộp thoai ,để
                //để vậy cho dễ phân biệt
                String makh=String.valueOf(khachhangtamthoi.get(0).getMakhachhang());
                params.put("MAHOPTHOAI",MAHOPTHOAI+"");
                params.put("MAKHACHHANG",makh);
                params.put("TINHTRANG",1+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //hàm này thì là kiểm tra dữ liệu của member có đang soạn tin hay không
    private void xoa_laycsdlTinnhan_DangsoantinWhereMAHTandMAKHchoADMIN(String url){
        arrayListTinnhan=new ArrayList<Class_Table_Tinnhan>();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getInt("TINHTRANG")==1){
                            //tinhtrangMember=true;
                            String url =urlpublic+"siteNhaHangvaKhachSan/delete_tinnhan_dangsoantin.php";
                            //nếu có thì xóa ngay lập tức
                            deletecsdlTinnhan_Dangsoantin(url);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi lay csdl : "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        requestQueue.add(jsonArrayRequest);
    }
    private void deletecsdlTinnhan_Dangsoantin(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(), "Hủy soạn", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Hủy soạn thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi xoa: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params=new HashMap<>();
                params.put("MAHOPTHOAI",MAHOPTHOAI+"");
                params.put("MAKHACHHANG",khachhangtamthoi.get(0).getMakhachhang()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_20));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Notification_thongbaothoat();
        return super.onOptionsItemSelected(item);
    }
    private void Notification_thongbaothoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuCHATSupportAdminActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thoát");
        builder.setMessage("Bạn muốn thoát và đóng hộp thoại chat ?");
        builder.setNegativeButton("Tôi đồng ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
                handler.removeCallbacks(runloadMessenger);
                handlerLoadDangsoantin.removeCallbacks(runnableLoadDangsoantin);
                handlerLoadCheckDangsoantin.removeCallbacks(runnableLoadCheckDangsoantin);
                handlerThuthapdulieutubanphimlientuc.removeCallbacks(runnableThuthapdulieutubanphimlientuc);
                handlerLoadDangsoantin3000ms.removeCallbacks(runnableLoadDangsoantin3000ms);

                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });
        builder.setPositiveButton("Tôi không chắc !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        //nếu đóng băng hàm này thì có thể gọi những hàm khác mà vẫn trong tình trạng ở activity đó
        //nếu không đóng băng hàm này
        //thì sẽ vừa hiện hành động
        //vừa thoát ra
        // super.onBackPressed();
        Notification_thongbaothoat();
    }

}


