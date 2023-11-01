package com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.util.ArrayList;

public class LoginOrSignupActivity extends AppCompatActivity {
    Intent intentMainActivity,intentLogin,intentSignup,intentForget;
    Button btnLogin ,btnSignup,btnForget,btnGuest;
    TextView txtLogoChaomung;
    Animation thunho,phongto1phan2,phongto1phan2sau1,phongto1phan2sau2,phongto1phan2sau3,tuhiensanganchologovanguoclai;
    //thu nghiem xem ket qua
    //ListView lisviewthunghiem;

    ArrayList<Class_Table_Khachhang> arrayListthunghiem;
    //thu nghiem xem ket qua



    int requestCodeLogin=111;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestlogin là code của activity này nhé !
        //chụp lại đi nhé
        if(requestCode==requestCodeLogin && resultCode==RESULT_OK){
            boolean confirm =data.getBooleanExtra("CONFIRMLOGIN",false);
            if(confirm==true){
                finish();
                //overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyen);
            }else{
                Toast.makeText(getApplicationContext(),"Vui long dang nhap hoac dang ki !",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);
        AnhXa();
        setOnClickButton();
        customActionBar();

        txtLogoChaomung.startAnimation(tuhiensanganchologovanguoclai);

      //  Cursor cursor =databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
        //iniView2();
       // iniView();
        //String[]data ={"hello" ,"xin chao "};
       // ArrayAdapter<String> phone=new ArrayAdapter<String>(LoginOrSignupActivity.this,R.layout.support_simple_spinner_dropdown_item,
        //        data);
       // listView.setAdapter(phone);


    }
    private void customActionBar(){
        //gán mũi tên trên action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set chữ label ở giữa trên action bar



    }

    private void setOnClickButton(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               v.startAnimation(thunho);
                startActivityForResult(intentLogin,requestCodeLogin);
                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
               // startActivity(intentLogin);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                startActivity(intentSignup);
                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                //Toast.makeText(getApplicationContext(),"thanh cong",Toast.LENGTH_SHORT).show();
                startActivity(intentForget);
                overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
            }
        });
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentrangtuduoilentren);
            }
        });

    }

    private void AnhXa(){
        btnLogin=findViewById(R.id.btn_loginorsignup_login);
        btnSignup=findViewById(R.id.btn_loginorsignup_signup);
        btnForget=findViewById(R.id.btn_loginorsignup_forget);
        btnGuest=findViewById(R.id.btn_loginorsignup_guest);
        txtLogoChaomung=findViewById(R.id.txt_main_logokinhchaoquykhach);

        intentMainActivity=getIntent();
        intentLogin =new Intent(LoginOrSignupActivity.this, LoginActivity.class);
        intentSignup=new Intent(LoginOrSignupActivity.this,SignupActivity.class);
        intentForget=new Intent(LoginOrSignupActivity.this,ForgetActivity.class);

        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiukhongquayve);
        phongto1phan2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongto1phan2);
        phongto1phan2sau1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongto1phan2sau1);
        phongto1phan2sau2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongto1phan2sau2);
        phongto1phan2sau3=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongto1phan2sau3);

        tuhiensanganchologovanguoclai=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tuhiensanganvanguoclaichologo);
    }
    private void Notification_taikhoankhongtontai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginOrSignupActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo khẩn !");
        builder.setMessage("Tài khoản không tồn tài !");
        builder.setNegativeButton("Tôi Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentrangtuduoilentren);
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
        overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentrangtuduoilentren);
    }
}
