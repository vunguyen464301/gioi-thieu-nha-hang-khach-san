package com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class ForgetActivity extends AppCompatActivity {
    private EditText edtSodienthoai,edtEmail,edtGhichu;
    private Button btn_send;
    private ProgressBar progressBar1;
    private LinearLayout lin3;
    private Animation thunho;
    String getNumberphone,getEmail,getGhichu;
    int dem;
    Intent intent;

    ArrayList<Class_Table_Khachhang> khachhangArrayList;

    Handler handlerTime =new Handler();
    Runnable runTime =new Runnable() {
        @Override
        public void run() {
           // Toast.makeText(getApplicationContext(),dem+"",Toast.LENGTH_SHORT).show();
            dem=dem-1;
            handlerTime.postDelayed(runTime,1000);
            if(dem==0){
                handlerTime.removeCallbacks(runTime);
                //ngừng quay
                lin3.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.INVISIBLE);

                if(khachhangArrayList.size()>0){
                   // Toast.makeText(getApplicationContext(),"So dien thoai nay da ton tai ,chuan bi doi pass !",Toast.LENGTH_SHORT).show();
                    String url =urlpublic+ "siteNhaHangvaKhachSan/insert_forgetpassword.php";
                    insertDatavaotableForget_Password(url);
                    Notification_tinvui();
                }else{
                   // Toast.makeText(getApplicationContext(),"So dien thoai khong ton tai ,doi mat khau that bai !",Toast.LENGTH_SHORT).show();
                    Notification_taikhoankhongtontai();
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        AnhXa();
        setOnClickButton();
        customActionBar();
    }
    private void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_19));
        }

    }
    private void setOnClickButton(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                attemp();
            }
        });
    }
    private void attemp(){
        dem=3;

        getNumberphone =edtSodienthoai.getText().toString();
        getEmail=edtEmail.getText().toString();
        getGhichu=edtGhichu.getText().toString();
        boolean cancel = false;
        View focusView = null;
        //thứ 1 tải bảng cơ sở dữ liệu xem có tồn tại hay không
        String url =urlpublic+ "siteNhaHangvaKhachSan/read_khachhang_where.php?SODT="+getNumberphone;
        //kiểm tra null
        if (getNumberphone.length()==0) {
            edtSodienthoai.setError(getString(R.string.error_field_required));
            focusView =edtSodienthoai;
            cancel = true;
        }else if(getEmail.length()==0){
            edtEmail.setError(getString(R.string.error_field_required));
            focusView = edtEmail;
            cancel = true;
        }

    //kiểm tra số điện thoại và email có thỏa mãn yêu cầu hay không
    //số điện thoại không đủ 10 hoặc 11 số là số điện thoại không hợp lệ ,cần duyệt xem là gì
    //thêm thuộc tính >0 để tránh nó chạy xuống vòng lặp này không thông báo đúng
        if(getNumberphone.length()>0 && isNumberphoneValid(getNumberphone)==false){
        //nếu không bé hơn 10 thì là lớn hơn 11 ,vì là vô trường hợp này thì chỉ có bé hoặc lớn hơn
        if(getNumberphone.length()<10){
            //lưu thông báo bên tay phải ở phần numberphoneview
            edtSodienthoai.setError(getString(R.string.error_invalid_numberphone_too_short));
            focusView=edtSodienthoai;
            //cancel =true thì là từ chối đăng nhập
            cancel = true;
            }else{
                edtSodienthoai.setError(getString(R.string.error_invalid_numberphone_too_long));
                focusView=edtSodienthoai;
                //cancel =true thì là từ chối đăng nhập
                cancel = true;
            }
            //kiểm tra email
        }else if(edtEmail.length()>0 &&   isEmailValid(getEmail)==false){
                edtEmail.setError(getString(R.string.error_invalid_email));
                focusView=edtEmail;
                cancel=true;


        }
        //phần này hiện thông báo hoặc cho phép đăng nhập
            if (cancel==true) {
            //xuất ra thông báo ghi chú bên tay phải
            focusView.requestFocus();
        } else {
            //tới chỗ này thì chỉ việc đổ dữ liệu xuống để kiểm tra
            // showProgress(true);
                lin3.setVisibility(View.INVISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
            //Kiểm tra tồn tại hay không rồi mới xóa được
                Toast.makeText(getApplicationContext(),"Đang kiểm tra" ,Toast.LENGTH_SHORT).show();
                laycsdlKHACHHANGonMySQL(url);
                handlerTime.postDelayed(runTime,0);

        //mAuthTask = new UserLoginTask(email, password);
        //mAuthTask.execute((Void) null);
    }
}

    private void insertDatavaotableForget_Password(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(),"Gửi thành công",Toast.LENGTH_SHORT).show();
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
                params.put("SODIENTHOAI",getNumberphone.trim().toString());
                params.put("EMAIL",getEmail.trim().toString());
                params.put("GHICHU",getGhichu.trim().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void laycsdlKHACHHANGonMySQL(String url)
    {

        khachhangArrayList.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Khachhang khachHang;
                        khachHang = new Class_Table_Khachhang(jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("SODIENTHOAI"),
                                jsonObject.getString("HINHANH"),
                                jsonObject.getString("TENKHACHHANG"),
                                jsonObject.getString("NGAYTHANGNAMSINH"),
                                jsonObject.getString("DIACHI"),
                                jsonObject.getString("MATKHAU"));
                        khachhangArrayList.add(khachHang);
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
    private void Notification_taikhoankhongtontai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle(getString(R.string.thongbao_khan));
        builder.setMessage(getString(R.string.thongbao_taikhoankhongtontai));
        builder.setNegativeButton(getString(R.string.thongbao_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);

            }
        });
        builder.setPositiveButton(getString(R.string.thongbao_tuchoi), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();
    }
    private void Notification_tinvui(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle(getString(R.string.thongbao_khan));
        builder.setMessage(getString(R.string.thongbao_ghichu));
        builder.setNegativeButton(getString(R.string.thongbao_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });


        AlertDialog dialog =builder.create();
        dialog.show();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //tách chuỗi ,trong chuỗi có kí hiệu @ thì mới là email hợp lệ
        //kiểm tra trong chuỗi có @ thì kiểm tra
        //bđếm mảng ,nếu là 1 thì ko có dấu @ ,nếu là 2 thì có dấu @ vì phải tách
       //hoặc là email.contain("@); kiểm tra chuỗi có chứa kí tự này hay không

        String[] chuoi =email.split("@");

        if(chuoi.length==2){
            //chiều dài mảng ==1 là mặc định ,==2 là có 1 @ ,==3 là có 2@
            //vi du xxx@yyy
            //thì tách ra được 2 mảng mới đúng
            if(isEmailValidcheckCHAM(chuoi[1])==true) {
                return true;
            }

        }
        return false;

    }

    private boolean isEmailValidcheckCHAM(String duoiemail) {
        //TODO: Replace this with your own logic
        //tách chuỗi ,trong chuỗi có kí hiệu @ thì mới là email hợp lệ
        //kiểm tra trong chuỗi có @ thì kiểm tra
        //bđếm mảng ,nếu là 1 thì ko có dấu @ ,nếu là 2 thì có dấu @ vì phải tách
        //hoặc là email.contain("@); kiểm tra chuỗi có chứa kí tự này hay không

        String[] cham =duoiemail.split("\\.");

        if(cham.length==2||cham.length==3){
            //chiều dài mảng ==1 là mặc định ,==2 là có 1 @ ,==3 là có 2@
            //vi du xxx@yyy
            //thì tách ra được 2 mảng mới đúng
            return true;
        }
        return false;

    }



    private boolean isNumberphoneValid(String numberphone) {
        //TODO: Replace this with your own logic
        //tách chuỗi ,đếm chuỗi đủ 10 hoặc 11
        if(numberphone.length()==10||numberphone.length()==11) {
            return true;
        }
        return false;
    }
    private void AnhXa(){
        edtSodienthoai=findViewById(R.id.editNumberphone_forget);
        edtEmail=findViewById(R.id.editEmail_forget);
        edtGhichu=findViewById(R.id.edtNhapghichu_forget);

        btn_send=findViewById(R.id.forget_button);
        progressBar1=findViewById(R.id.forget_progress);

        intent=getIntent();
        khachhangArrayList=new ArrayList<Class_Table_Khachhang>();

        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
        lin3 =findViewById(R.id.lin3);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        //nếu đóng băng hàm này thì có thể gọi những hàm khác mà vẫn trong tình trạng ở activity đó
        //nếu không đóng băng hàm này
        //thì sẽ vừa hiện hành động
        //vừa thoát ra
        // super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
    }
}
