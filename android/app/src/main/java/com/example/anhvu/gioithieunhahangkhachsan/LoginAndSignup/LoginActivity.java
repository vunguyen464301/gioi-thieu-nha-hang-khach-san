package com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.databaseNhaHangKhachSan;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class LoginActivity extends AppCompatActivity {
    //b1:lay csdl xuong
    //b2:bo vo class
    //b3:arraylist chua class
    //b4:su dung ham runtime
    //b5:sau 2s lay sodienthoai va mat khau tren edittext
    //b6:so sanh sodienthoai va matkhau co khop hay khong voi du lieu moi tai xuong ,
    //dieu kien la nhập đúng số điện thoại
    private AutoCompleteTextView mNumberphoneView;
    private EditText mPasswordView;
    private ProgressBar progressBar1;
    private Button btnLogin;
    private String getNumberphone,getPassword;
    private Animation thunho;
    private LinearLayout lin1;


    private int dem;
    private CheckBox checkBoxSaveAccount;
    Intent intent;


    ListView lisviewthunghiem;

    ArrayList<Class_Table_Khachhang> arrayListthunghiem;



    Class_Table_Khachhang class_table_khachhang;
    ArrayList<Class_Table_Khachhang> khachhangArrayList;
    //chạy dữ liệu để kiểm tra đăng nhập ,phải chờ 2s lấy kết quả xuống rồi mới lấy được
    private Handler handlerTime= new Handler();

    private Runnable runTime =new Runnable() {
        @Override
        public void run() {
            dem =dem-1;
           // Toast.makeText(getApplicationContext(), dem+"" , Toast.LENGTH_SHORT).show();
            //sau 5s se cap nhat
            handlerTime.postDelayed(this,1000);
            if(dem==0) {
                handlerTime.removeCallbacks(runTime);
                lin1.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.INVISIBLE);

                // Toast.makeText(getApplicationContext(),c.getMatkhau()+" " +getPassword ,Toast.LENGTH_SHORT).show();

                if (khachhangArrayList.size() > 0) {
                    Class_Table_Khachhang c = khachhangArrayList.get(0);
                    if (c.getMatkhau().equals(getPassword.toString())) {
                       // Toast.makeText(getApplicationContext(), "Mat khau dung", Toast.LENGTH_SHORT).show();
                        Cursor cursor =databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
                        if (checkBoxSaveAccount.isChecked()) {

                            saveAccount(c.getMakhachhang(), c.getSodienthoai(), c.getHinhanh(), c.getTenkhachhang(), c.getNgaythangnamsinh(),
                                    c.getDiachi(), c.getMatkhau());
                            //bỏ new vô đây thì khởi tạo mới luôn ,không cần xóa
                            khachhangtamthoi =new ArrayList<Class_Table_Khachhang>();
                            khachhangtamthoi.add(new Class_Table_Khachhang(c.getMakhachhang(), c.getSodienthoai(), c.getHinhanh(), c.getTenkhachhang(), c.getNgaythangnamsinh(),
                                    c.getDiachi(), c.getMatkhau()));

                         //   Toast.makeText(getApplicationContext(),"luu tai khoan "+khachhangArrayList.size()+
                           //         " "+cursor.getCount() +" "+khachhangtamthoi.size() ,Toast.LENGTH_SHORT).show();
                        }else {
                            //bỏ vô array list biến toàn cục để activity nào cũng có thể sử dụng ,nhưng kkhi không lưu thì khi tắt máy
                            //thì phải đăng nhập lại
                            khachhangtamthoi =new ArrayList<Class_Table_Khachhang>();
                            khachhangtamthoi.add(new Class_Table_Khachhang(c.getMakhachhang(), c.getSodienthoai(), c.getHinhanh(), c.getTenkhachhang(), c.getNgaythangnamsinh(),
                                    c.getDiachi(), c.getMatkhau()));

                          //  Toast.makeText(getApplicationContext(), "khong luu tai khoan " + khachhangArrayList.size()
                           //         + " " + cursor.getCount() +" "+khachhangtamthoi.size() , Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                        //kiểm tra xong thì phải ẩn progressbar đi rồi hiển thị layout đó

                        //trả về kết quả nhé
                        //chụp lại đi nhé
                        getNumberphoneondatabase(getNumberphone);
                        intent.putExtra("CONFIRMLOGIN",true);
                        setResult(RESULT_OK,intent);
                        Notification_thanhcong();

                        //iniView();
                    } else if (!(c.getMatkhau().equals(getPassword.toString()))) {
                        Notification_matkhaubisai();
                    }
                } else {
                    Notification_taikhoankhongtontai();
                }


            }
        }
    };

    private void loadautocompletexviewSodienthoai(){

        //lấy dữ liệu ra bỏ vào mảng
        //bảng numberphone_khachang dung de luu so dien thoai da dang nhap vao mang
        Cursor cursor = databaseNhaHangKhachSan.query("NUMBERPHONE_KHACHHANG", null, null, null, null, null, null);

        if(cursor.getCount()!=0) {
            ArrayList<String>arrayListsodienthoai =new ArrayList<>();
            cursor.moveToFirst();
            while (!(cursor.isAfterLast())) {
                arrayListsodienthoai.add(cursor.getString(1));
                cursor.moveToNext();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListsodienthoai);
            mNumberphoneView.setAdapter(adapter);
        }
    }
    private void getNumberphoneondatabase(String data){
        ArrayList<String> arraylistsodienthoai =new ArrayList<>();
        ContentValues values =new ContentValues();
        //kiếm tra trong database có số điện thoại này chưa
        //nếu chưa thì thêm vào ,nếu có rồi thì khỏi thêm vào
        Cursor cursor = databaseNhaHangKhachSan.query("NUMBERPHONE_KHACHHANG", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!(cursor.isAfterLast())) {
            arraylistsodienthoai.add(cursor.getString(1));
            cursor.moveToNext();
        }
        //so sánh
        //nếu kiểm tra trong arraylist có chưa phần tử này thì trả về true
        //thì không cần phải lưu
        if(arraylistsodienthoai.contains(data)==false){
            values.put("sodienthoai",data);
            databaseNhaHangKhachSan.insert("NUMBERPHONE_KHACHHANG",null,values);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        setButton();
        loadautocompletexviewSodienthoai();

        customActionBar();

    }

    private void AnhXa(){
        mNumberphoneView = (AutoCompleteTextView) findViewById(R.id.numberphone);
        mPasswordView = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.sign_in_button);

        //mLoginFormView = findViewById(R.id.login_form);
        progressBar1 = findViewById(R.id.login_progress);
        khachhangArrayList = new ArrayList<Class_Table_Khachhang>();
        checkBoxSaveAccount =findViewById(R.id.checkboxSaveAccout);
        intent=getIntent();
        thunho=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);

        lin1=findViewById(R.id.lin1);
    }
    private void setButton(){

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(thunho);



                attemptLogin();
            }
        });

    }

    private void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_19));
        }

    }
    private void attemptLogin() {
        dem=3;
        getNumberphone =mNumberphoneView.getText().toString();
        getPassword=mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        String url =urlpublic+ "siteNhaHangvaKhachSan/read_khachhang_where.php?SODT="+getNumberphone;





//kiểm tra null
        if (getNumberphone.length()==0) {
            mNumberphoneView.setError(getString(R.string.error_field_required));
            focusView = mNumberphoneView;
            cancel = true;
        }else if(getPassword.length()==0){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        //kiểm tra số điện thoại và pass có thỏa mãn yêu cầu hay không
        //số điện thoại không đủ 10 hoặc 11 số là số điện thoại không hợp lệ ,cần duyệt xem là gì
        //thêm thuộc tính >0 để tránh nó chạy xuống vòng lặp này không thông báo đúng
        if(getNumberphone.length()>0 && isNumberphoneValid(getNumberphone)==false){
            //nếu không bé hơn 10 thì là lớn hơn 11 ,vì là vô trường hợp này thì chỉ có bé hoặc lớn hơn
            if(getNumberphone.length()<10){
                //lưu thông báo bên tay phải ở phần numberphoneview
                mNumberphoneView.setError(getString(R.string.error_invalid_numberphone_too_short));
                focusView=mNumberphoneView;
                //cancel =true thì là từ chối đăng nhập
                cancel = true;
            }else{
                mNumberphoneView.setError(getString(R.string.error_invalid_numberphone_too_long));
                focusView=mNumberphoneView;
                //cancel =true thì là từ chối đăng nhập
                cancel = true;
            }
            //kiểm tra mật khẩu
        }else if(getPassword.length()>0 &&   isPasswordValid(getPassword)==false){
            if(getPassword.length()<=4){
                mPasswordView.setError(getString(R.string.error_invalid_password_too_short));
                focusView=mPasswordView;
                cancel=true;
            }else {
                mPasswordView.setError(getString(R.string.error_invalid_password_too_long));
                focusView=mPasswordView;
                cancel=true;
            }
        }

        //phần này hiện thông báo hoặc cho phép đăng nhập
        if (cancel==true) {
            //xuất ra thông báo ghi chú bên tay phải
            focusView.requestFocus();
        } else {
            //tới chỗ này thì chỉ việc đổ dữ liệu xuống để kiểm tra
            lin1.setVisibility(View.INVISIBLE);
            progressBar1.setVisibility(View.VISIBLE);

            //Kiểm tra tồn tại hay không rồi mới xóa được
            databaseNhaHangKhachSan.delete("KHACHHANG", "sodienthoai=?", new String[]{getNumberphone + ""});
            laycsdlKHACHHANGonMySQL(url);
            handlerTime.postDelayed(runTime,0);

            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
        }
    }







    private void saveAccount(int makh,String sodienthoai,String hinhanh,String tenkh,String NTNsinh,String diachi ,String matkhau){
        addDataKHACHHANG(makh,sodienthoai,hinhanh,tenkh,NTNsinh,diachi,matkhau);
    }
    private void addDataKHACHHANG(int makh,String sodt,String hinhanh,String tenkh,String NTNsinh,String diachi,String matkhau){
        ContentValues values =new ContentValues();
        values.put("makh",makh);
        values.put("sodienthoai",sodt);
        values.put("hinhanh",hinhanh);
        values.put("tenkh",tenkh);
        values.put("NTNamsinh",NTNsinh);
        values.put("diachi",diachi);
        values.put("matkhau",matkhau);

        if(databaseNhaHangKhachSan.insert("KHACHHANG",null,values)==-1){
            Toast.makeText(getApplicationContext(),"Lưu thất bại",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_SHORT).show();
        }


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
    private void Notification_taikhoankhongtontai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo khẩn !");
        builder.setMessage("Tài khoản không tồn tại !");
        builder.setNegativeButton("Tôi Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();
    }
    private void Notification_thanhcong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo khẩn !");
        builder.setMessage("Đăng nhập Thành công");
        builder.setNegativeButton("Tôi Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentrangtuduoilentren);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_matkhaubisai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Mật khẩu bị sai !");
        builder.setMessage("Nhập lại mật khẩu");
        builder.setNegativeButton("Tôi Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    /*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    */
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





