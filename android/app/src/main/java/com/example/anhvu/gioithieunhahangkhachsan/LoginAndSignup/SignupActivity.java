package com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class SignupActivity extends AppCompatActivity {
    private EditText edtNhapsodienthoai,edtNhaptenKH,edtNhapdiachi,edtNhapmatkhau1,edtNhapmatkhau2;
    private TextView txtNhapNTNsinh;
    private Button btnDocdieukhoan,btnSignup,btnChonngaysinh;
    private CheckBox checkBoxDongyDieukhoan;
    String getNhapsodienthoai,getNhaptenKH,getNhapNTNsinh,getNhapdiachi,getNhapmatkhau1,getNhapmatkhau2;
    Calendar calendar;
    private ArrayList<Class_Table_Khachhang> khachhangArrayList;
    private Animation thunho,tuAnsangHien,tuHiensangAn;
    private ProgressBar progressBar1;
    private LinearLayout lin2;

    int ngayhientai,ngaydachon;
    int thanghientai,thangdachon;
    int namhientai,namdachon;

    Intent intent,intentdieukhoan;

    int dem;
    private Handler handlerTime=new Handler();
    private Runnable runTime =new Runnable() {
        @Override
        public void run() {
            dem=dem-1;
            //Toast.makeText(getApplicationContext(), dem+"" , Toast.LENGTH_SHORT).show();
            handlerTime.postDelayed(this,1000);
            if(dem==0){
                handlerTime.removeCallbacks(runTime);

                lin2.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.INVISIBLE);

                if(khachhangArrayList.size()>0){
                    //Toast.makeText(getApplicationContext(), "so dien thoai da ton tai ,khong the tao tai khoan" , Toast.LENGTH_SHORT).show();
                    Notification_taikhoandatontai();
                }else{
                    //Toast.makeText(getApplicationContext(), "so dien thoai khong ton tai ,duoc phep tao tai khoan" , Toast.LENGTH_SHORT).show();
                    Notification_xacnhandangki();



                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AnhXa();

        txtNhapNTNsinh.setText("Nhập ngày/tháng/năm sinh");

        //khởi tạo biến
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //lấy thời gian hiện tại
        //txtNhapNTNsinh.setText(sdf.format(calendar.getTime()));
        String layngayhientai = sdf.format(calendar.getTime());
        String[] manghientai = layngayhientai.split("/");
        ngayhientai = Integer.parseInt(manghientai[0]);
        thanghientai = Integer.parseInt(manghientai[1]);
        namhientai = Integer.parseInt(manghientai[2]);


        setOnclickButton();
        customActionBar();

        //getSupportActionBar().setTitle("hello");

    }
    private void customActionBar(){
        //gán mũi tên trên action bar
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_19));
        }
        //set chữ label ở giữa trên action bar
    }
    private void setOnclickButton(){
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                dem = 3;
                getNhapsodienthoai = edtNhapsodienthoai.getText().toString();
                getNhaptenKH = edtNhaptenKH.getText().toString();
                getNhapNTNsinh = txtNhapNTNsinh.getText().toString();
                getNhapdiachi = edtNhapdiachi.getText().toString();
                getNhapmatkhau1 = edtNhapmatkhau1.getText().toString();
                getNhapmatkhau2 = edtNhapmatkhau2.getText().toString();

                if(txtNhapNTNsinh.getText().toString().equals("Nhập ngày/tháng/năm sinh")){
                    //kiểm tra trước khi insert vào bảng nếu là ngày tháng năm sinh chưa click vào thì khi insert
                    //vào thì phải là null
                    getNhapNTNsinh=String.valueOf("");
                    if (checkBoxDongyDieukhoan.isChecked()) {
                        attemptSignup();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Ban phai dong y dieu khoan cua chung toi !", Toast.LENGTH_SHORT).show();
                        Notification_chuadongydieukhoan();
                    }
                }else {
                    //thangdachon=thangdachon;
                    //thanghientai=thanghientai;
                    //trong trường hợp này thì ,năm lớn hơn thì ko cho ,bé hơn thì cho ,bằng thì kiểm tra tháng
                    //tháng bé hơn thì cho ,nếu bằng thì kiểm tra tiếp ngày
                    //nếu ngày đã chọn bé hơn thì cho ,tất cả điều kiện còn lại thì phải nhập lại
                    if (namdachon > namhientai) {
                        // Toast.makeText(getApplicationContext(), "Ngay sinh khong hop le ,vui" +
                        //       " long chon lai", Toast.LENGTH_SHORT).show();
                        Notification_ngaysinhkhonghople();
                    } else if (namdachon == namhientai) {
                        if (thangdachon > thanghientai) {
                            //sai
                            Notification_ngaysinhkhonghople();
                        } else if (thangdachon == thanghientai) {
                            if (ngaydachon >= ngayhientai) {
                                //sai
                                Notification_ngaysinhkhonghople();
                            } else {
                                //ok
                                if (checkBoxDongyDieukhoan.isChecked()) {
                                    attemptSignup();
                                } else {
                                    //Toast.makeText(getApplicationContext(), "Ban phai dong y dieu khoan cua chung toi !", Toast.LENGTH_SHORT).show();
                                    Notification_chuadongydieukhoan();
                                }
                            }
                        } else {
                            //ok
                            if (checkBoxDongyDieukhoan.isChecked()) {
                                attemptSignup();
                            } else {
                                //Toast.makeText(getApplicationContext(), "Ban phai dong y dieu khoan cua chung toi !", Toast.LENGTH_SHORT).show();
                                Notification_chuadongydieukhoan();
                            }
                        }

                    } else {
                        if (checkBoxDongyDieukhoan.isChecked()) {
                            attemptSignup();
                        } else {
                            // Toast.makeText(getApplicationContext(), "Ban phai dong y dieu khoan cua chung toi !", Toast.LENGTH_SHORT).show();
                            Notification_chuadongydieukhoan();
                        }
                    }
                }
            }
        });


        btnDocdieukhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                //Toast.makeText(getApplicationContext(),"ngay hien tai: "+ngayhientai+"/"+thanghientai+"/"+namhientai
                //+"\nNgay da chon: "+ngaydachon+"/"+thangdachon+"/"+namdachon+"/",Toast.LENGTH_SHORT).show();
                startActivity(intentdieukhoan);
                overridePendingTransition(R.anim.tuansanghien,R.anim.giunguyentuthe);
            }
        });



        btnChonngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtNhapNTNsinh.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        ngaydachon = dayOfMonth;
                        thangdachon = month + 1;
                        namdachon = year;
                        calendar.set(year, month, dayOfMonth);
                    }
                };
                //so sánh nếu là người dùng chưa click vào thì lấy ngày hiện tại
                //set trong đây thì tháng luôn phải -1
                if (txtNhapNTNsinh.getText().toString().equals("Nhập ngày/tháng/năm sinh")) {
                    //ngày tháng năm hiện tại không bao giờ cần cộng thêm hay trừ thêm
                    int ngaydachon1 = ngayhientai;
                    int thangdachon1 = thanghientai-1;
                    int namdachon1 = namhientai;

                    //hiện thị ngày tháng năm lên button
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, callback, namdachon1, thangdachon1, ngaydachon1);
                    datePickerDialog.show();
                } else {

                    String text = txtNhapNTNsinh.getText().toString();
                    String[] chuoi = text.split("/");
                    int ngaydachon1 = Integer.parseInt(chuoi[0]);
                    int thangdachon1 = Integer.parseInt(chuoi[1]) - 1;
                    int namdachon1 = Integer.parseInt(chuoi[2]);

                    //hiện thị ngày tháng năm lên button
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, callback, namdachon1, thangdachon1, ngaydachon1);
                    datePickerDialog.show();

                   // Toast.makeText(getApplicationContext(),txtNhapNTNsinh.getText(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void attemptSignup() {
        dem=3;
        boolean cancel = false;
        View focusView = null;

        String url =urlpublic+  "siteNhaHangvaKhachSan/read_khachhang_where.php?SODT="+getNhapsodienthoai;

        //kiểm tra null
        if (getNhapsodienthoai.length()==0) {
            edtNhapsodienthoai.setError(getString(R.string.error_field_required));
            focusView = edtNhapsodienthoai;
            cancel = true;
        }else if(getNhapmatkhau1.length()==0){
            edtNhapmatkhau1.setError(getString(R.string.error_field_required));
            focusView = edtNhapmatkhau1;
            cancel = true;
        }else if(getNhapmatkhau2.length()==0){
            edtNhapmatkhau2.setError(getString(R.string.error_field_required));
            focusView = edtNhapmatkhau2;
            cancel = true;
        }
        //kiểm tra số điện thoại và pass có thỏa mãn yêu cầu hay không
        //số điện thoại không đủ 10 hoặc 11 số là số điện thoại không hợp lệ ,cần duyệt xem là gì
        //thêm thuộc tính >0 để tránh nó chạy xuống vòng lặp này không thông báo đúng
        if(getNhapsodienthoai.length()>0 && isNumberphoneValid(getNhapsodienthoai)==false){
            //nếu không bé hơn 10 thì là lớn hơn 11 ,vì là vô trường hợp này thì chỉ có bé hoặc lớn hơn
            if(getNhapsodienthoai.length()<10){
                //lưu thông báo bên tay phải ở phần numberphoneview
                edtNhapsodienthoai.setError(getString(R.string.error_invalid_numberphone_too_short));
                focusView=edtNhapsodienthoai;
                //cancel =true thì là từ chối đăng nhập
                cancel = true;
            }else{
                edtNhapsodienthoai.setError(getString(R.string.error_invalid_numberphone_too_long));
                focusView=edtNhapsodienthoai;
                //cancel =true thì là từ chối đăng nhập
                cancel = true;
            }
            //kiểm tra mật khẩu
        }else if(getNhapmatkhau1.length()>0 &&   isPasswordValid(getNhapmatkhau1)==false){
            if(getNhapmatkhau1.length()<=4){
                edtNhapmatkhau1.setError(getString(R.string.error_invalid_password_too_short));
                focusView=edtNhapmatkhau1;
                cancel=true;
            }else {
                edtNhapmatkhau1.setError(getString(R.string.error_invalid_password_too_long));
                focusView=edtNhapmatkhau1;
                cancel=true;
            }
            //mật khẩu và nhập lại mật khẩu trùng nhau mới cho đăng kí
        }else if(getNhapmatkhau2.length()>0 &&getNhapmatkhau2.length()>0  && !getNhapmatkhau1.equals(getNhapmatkhau2.toString())){
            edtNhapmatkhau2.setError(getString(R.string.error_fail_password_khongtrung));
            focusView=edtNhapmatkhau2;
            cancel=true;
        }
        //phần này hiện thông báo hoặc cho phép đăng nhập
        if (cancel==true) {
            //xuất ra thông báo ghi chú bên tay phải
            focusView.requestFocus();
        } else {
            //tới chỗ này thì chỉ việc đổ dữ liệu xuống để kiểm tra
            lin2.setVisibility(View.INVISIBLE);
            progressBar1.setVisibility(View.VISIBLE);

            //Toast.makeText(getApplicationContext(),"da thanh cong",Toast.LENGTH_SHORT).show();
            laycsdlKHACHHANGonMySQL(url);

            handlerTime.postDelayed(runTime,0);
        }
    }


    private void insertDatavaotableKHACHHANG(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
                }

                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Lỗi đăng kí : "+ volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String ,String> params =new HashMap<>();
                //trim cắt khoảng trắng đầu đuôi
                params.put("SODIENTHOAI",getNhapsodienthoai.trim().toString());
                params.put("TENKHACHHANG",getNhaptenKH.trim().toString());
                params.put("NGAYTHANGNAMSINH",getNhapNTNsinh.trim().toString());
                params.put("DIACHI",getNhapdiachi.trim().toString());
                params.put("MATKHAU",getNhapmatkhau2.trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void laycsdlKHACHHANGonMySQL(String url) {
        dem=3;

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

    private boolean isNumberphoneValid(String numberphone) {
        //TODO: Replace this with your own logic
        //tách chuỗi ,đếm chuỗi đủ 10 hoặc 11
        if(numberphone.length()==10||numberphone.length()==11) {
            return true;
        }
        return false;
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if(password.length()>4 && password.length()<20){
            return true;
        }
        return false;
    }
    private void Notification_xacnhandangki(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn chắc chắn đăng kí ?");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // dialogInterface.cancel();
                //Notification_thanhcong();
                //Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                String url = urlpublic+"siteNhaHangvaKhachSan/insert_khachhang.php";
                insertDatavaotableKHACHHANG(url);

            }
        });
        builder.setPositiveButton("Từ chối", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();
    }
    private void Notification_taikhoandatontai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Đăng kí thất bại !");
        builder.setMessage("Đăng kí thất bại do tài khoản đã đăng kí rồi !");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();
    }
    private void Notification_ngaysinhkhonghople() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Ngày sinh không hơp lệ ,vui lòng nhập lại");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_chuadongydieukhoan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn chưa đồng ý điều khoản của chúng tôi ,vui lòng đồng ý !");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void AnhXa(){
        edtNhapsodienthoai=findViewById(R.id.edtNhapnumberphone);
        edtNhaptenKH=findViewById(R.id.edtNhaphovaten);
        txtNhapNTNsinh=findViewById(R.id.edtNhapNgaythangnamsinh);
        edtNhapdiachi=findViewById(R.id.edtNhapdiachia);
        edtNhapmatkhau1=findViewById(R.id.edtpassword1);
        edtNhapmatkhau2=findViewById(R.id.edtpassword2);

        btnDocdieukhoan=findViewById(R.id.btnDocdieukhoan);
        btnSignup=findViewById(R.id.sign_up_button);
        checkBoxDongyDieukhoan=findViewById(R.id.checkboxdongy);

        khachhangArrayList=new ArrayList<Class_Table_Khachhang>();
        btnChonngaysinh=findViewById(R.id.btn_chonngaysinh);

        intent=getIntent();
        intentdieukhoan=new Intent(SignupActivity.this,dieukhoanActivity.class);

        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
        tuAnsangHien=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tuansanghien);
        tuHiensangAn=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tuhiensangan);

        progressBar1=findViewById(R.id.signup_progress);
        lin2=findViewById(R.id.lin2);
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
