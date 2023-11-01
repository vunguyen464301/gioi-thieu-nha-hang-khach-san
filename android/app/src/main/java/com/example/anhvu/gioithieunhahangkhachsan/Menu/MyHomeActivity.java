package com.example.anhvu.gioithieunhahangkhachsan.Menu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Khachhang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.databaseNhaHangKhachSan;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class MyHomeActivity extends AppCompatActivity {
    EditText edtName,edtDiachi,edtPass;
    TextView txtNumberphone,txtNgaythangnamsinh;
    Button btnLuuthongtin,btnNhapngaythangnamsinh;
    Intent intent;

    private ActionBarDrawerToggle toggle;
    Calendar calendar;
    SimpleDateFormat sdf;

    ProgressBar progressBar1;
    LinearLayout lin1;
    RelativeLayout re1;
    Animation thunho;

    ArrayList<Class_Table_Khachhang> khachhangArrayList;
    int ngayhientai,thanghientai,namhientai;
    int ngaydachon,thangdachon,namdachon;
    String getPassword;
    int dem;

    private Handler hanlHandlerUpdateThongtin=new Handler();
    private Runnable runnableUpdateThongtin=new Runnable() {
        @Override
        public void run() {
            dem=dem-1;
            hanlHandlerUpdateThongtin.postDelayed(runnableUpdateThongtin,1000);


            re1.setVisibility(View.VISIBLE);
            lin1.setVisibility(View.VISIBLE);
            progressBar1.setVisibility(View.INVISIBLE);

            if(dem==0){
                hanlHandlerUpdateThongtin.removeCallbacks(runnableUpdateThongtin);

                String gethotenkh=edtName.getText().toString();
                String getngaysinh=txtNgaythangnamsinh.getText().toString();
                //kiểm tra lần nữa để insert vào bảng mysql và sqlite
                if(txtNgaythangnamsinh.getText().toString().equals("Nhập ngày/tháng/năm sinh")){
                    //nếu là trong chuỗi có chứa kí tự này ,nghĩa là chưa bấm vô thì là người dùng insert dữ liệu vào không đưuọc hiện thị ngày sinh
                    getngaysinh=String.valueOf("");
                }

                String getdiachi=edtDiachi.getText().toString();

                Class_Table_Khachhang kh=khachhangtamthoi.get(0);
                kh.setTenkhachhang(gethotenkh);
                //tùy chọn là khi người dùng chưa click vào button thì ko insert ngày tháng năm sinh lên đo
                kh.setNgaythangnamsinh(getngaysinh);
                kh.setDiachi(getdiachi);

                Cursor cursor =databaseNhaHangKhachSan.query("KHACHHANG",null,null,null,null,null,null) ;
                if(cursor.getCount()>0){
                    ContentValues updateUp =new ContentValues();
                    //lấy dữ liệu để thay đổi
                    updateUp.put("makh",kh.getMakhachhang());
                    updateUp.put("sodienthoai",kh.getSodienthoai());
                    updateUp.put("hinhanh",kh.getHinhanh());
                    updateUp.put("tenkh",kh.getTenkhachhang());
                    updateUp.put("NTNamsinh",kh.getNgaythangnamsinh());


                    updateUp.put("diachi",kh.getDiachi());
                    updateUp.put("matkhau",kh.getMatkhau());
                    //thay đổi dữ liệu trong database
                    databaseNhaHangKhachSan.update("KHACHHANG",updateUp,"makh=?",new String[]{kh.getMakhachhang()+""});
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công trong Database", Toast.LENGTH_SHORT).show();

                }
                String urlcapnhat=urlpublic+"siteNhaHangvaKhachSan/update_khachhang.php";
                capnhatTableKHACHHANG(urlcapnhat,kh.getSodienthoai(),kh.getTenkhachhang(),kh.getNgaythangnamsinh(),kh.getDiachi());

                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);
        AnhXa();
        setView();
        customActionBar();
    }

    private void setView(){
        Class_Table_Khachhang cd=khachhangtamthoi.get(0);

        txtNumberphone.setText(cd.getSodienthoai()+"");
        edtName.setText(cd.getTenkhachhang()+"");
        //set hiện thị ngày tháng năm sinh
        if(khachhangtamthoi.get(0).getNgaythangnamsinh().toString().length()==0){
            txtNgaythangnamsinh.setText(R.string.prompt_born);
        }else {
            txtNgaythangnamsinh.setText(cd.getNgaythangnamsinh() + "");
        }
        edtDiachi.setText(cd.getDiachi());

        //lấy 2 thuật toán này ra ngoài vì là khi không click vào nó mới lấy dữ liệu thì mới cho thay đổi ,
        //nghĩa là phải cho nó lấy dữ liệu ,khi không cần click vào thay đổi

        calendar = Calendar.getInstance();
        sdf= new SimpleDateFormat("dd/MM/yyyy");
        String hientai =sdf.format(calendar.getTime()).toString();
        String[]manghientai =hientai.split("/");
        ngayhientai =Integer.parseInt(manghientai[0]);
        thanghientai=Integer.parseInt(manghientai[1]);
        namhientai =Integer.parseInt(manghientai[2]);

        btnLuuthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNgaythangnamsinh.getText().toString().equals("Nhập ngày/tháng/năm sinh")){
                   // Toast.makeText(getApplicationContext(),"nguoi dung chua click" ,Toast.LENGTH_SHORT).show();
                    attemptUpdate();
                }else {

                   // Toast.makeText(getApplicationContext(),"da click" ,Toast.LENGTH_SHORT).show();
                    kiemtrangaysinhhople();
                }//Toast.makeText(getApplicationContext(),"Ngay hien tai" +ngayhientai+"/"+thanghientai+"/"+namhientai+"\n"+"ngay da chon: "+ngaydachon+"/"+thangdachon+"/"+namdachon,Toast.LENGTH_SHORT).show();
                //kiểm tra ngày sinh hợp lệ thì thay đổi trong database,khachangtamthoi
                //update trong bảng khachang
            }
        });
        btnNhapngaythangnamsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                //lấy thời gian hiện tại
                // txtNgaythangnamsinh.setText(sdf.format(calendar.getTime()));
                DatePickerDialog.OnDateSetListener callback= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtNgaythangnamsinh.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        ngaydachon=dayOfMonth;
                        thangdachon=month+1;
                        namdachon=year;
                        calendar.set(year,month,dayOfMonth);
                    }
                };
                //nếu ngày tháng năm sinh null thì là nó kiểm tra lấy của ngày hiện tại ,nếu không lấy được thì bị lôi
                //thế nên bắt buộc nó phải lấy được ngày tháng năm hiện tại khi ngày sinh null
                String text=txtNgaythangnamsinh.getText().toString();
                if(text.equals("Nhập ngày/tháng/năm sinh")){
                    int ngaydachon1 = ngayhientai;
                    int thangdachon1 = thanghientai-1;
                    int namdachon1 = namhientai;

                    //hiện thị ngày tháng năm lên button
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MyHomeActivity.this, callback, namdachon1, thangdachon1, ngaydachon1);
                    datePickerDialog.show();
                }else {
                    //còn trường hợp này thì ngày tháng năm sinh có tồn tại
                  //Toast.makeText(getApplicationContext(),text+"",Toast.LENGTH_SHORT).show();
                    String[] chuoi = text.split("/");
                    int ngaydachon1 = Integer.parseInt(chuoi[0]);
                    int thangdachon1 = Integer.parseInt(chuoi[1])-1;
                    int namdachon1 = Integer.parseInt(chuoi[2]);

                    //hiện thị ngày tháng năm lên button
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MyHomeActivity.this, callback, namdachon1, thangdachon1, ngaydachon1);
                    datePickerDialog.show();
                }
            }
        });

    }


    private void attemptUpdate() {
        //dem=3;
        getPassword=edtPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        String url =urlpublic+ "siteNhaHangvaKhachSan/read_khachhang_where.php?SODT="+khachhangtamthoi.get(0).getSodienthoai();
        //kiểm tra null
        if(getPassword.length()==0){
            edtPass.setError(getString(R.string.error_field_required));
            focusView =edtPass;
            cancel = true;
        }
        //kiểm tra mật khẩu
        else if((getPassword.length()>0 &&getPassword.length()<=4)|| getPassword.length()>20){
            if(getPassword.length()<=4){
                edtPass.setError(getString(R.string.error_invalid_password_too_short));
                focusView=edtPass;
                cancel=true;
            }else {
                edtPass.setError(getString(R.string.error_invalid_password_too_long));
                focusView=edtPass;
                cancel=true;
            }
        }
        else if(!(getPassword.toString().trim().equals(khachhangtamthoi.get(0).getMatkhau()))){
            edtPass.setError(getString(R.string.error_fail_password_khongtrung));
            focusView=edtPass;
            cancel=true;
        }

        //phần này hiện thông báo hoặc cho phép đăng nhập
        if (cancel==true) {
            //xuất ra thông báo ghi chú bên tay phải
            focusView.requestFocus();
        } else {
            //tới chỗ này thì chỉ việc đổ dữ liệu xuống để kiểm tra
            // showProgress(true);
            Notification_saveThongtin();
            }
            //thành công thì cập nhật trong bảng database ,khachhangtamthoi ,bảng KHACHHANG
            //nhớ kiểm tra database có dữ liệu hay không

            // laycsdlKHACHHANGonMySQL(url);
            // handlerTime.postDelayed(runTime,0);

            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
        }

    private void capnhatTableKHACHHANG(String url, final String sodienthoai, final String tenkhachhang, final String ngaythangnamsinh, final String diachi) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Loi: " + volleyError.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sodienthoai",sodienthoai);
                params.put("tenkhachhang", tenkhachhang);
                params.put("ngaythangnamsinh", ngaythangnamsinh);
                params.put("diachi", diachi);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa(){
        edtName=findViewById(R.id.edtNhaphovaten_Menu_Myhome);
        edtDiachi=findViewById(R.id.edtNhapdiachia_Menu_Myhome);
        edtPass=findViewById(R.id.edtpassword_Menu_Myhome);
        txtNumberphone=findViewById(R.id.edtNhapnumberphone_Menu_Myhome);
        txtNgaythangnamsinh=findViewById(R.id.edtNhapNgaythangnamsinh_Menu_Myhome);
        btnLuuthongtin=findViewById(R.id.btnLuuThongTin_Menu_Myhome);
        btnNhapngaythangnamsinh=findViewById(R.id.btn_chonngaysinh_Menu_Myhome);
        intent=getIntent();
        khachhangArrayList=new ArrayList<Class_Table_Khachhang>();

        lin1=findViewById(R.id.lin1_Menu_Myhome);
        re1=findViewById(R.id.re1_Menu_Myhome);
        progressBar1=findViewById(R.id.update_progress_Menu_Myhome);
        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);

    }
    private void kiemtrangaysinhhople() {
        //nếu người dùng chưa click vào button chọn ngày sinh thì khi insert vô bảng thì phải để trống
        if (namdachon > namhientai) {
            //Toast.makeText(getApplicationContext(), "Ngày sinh không hợp lệ vui lòng chọn lại" , Toast.LENGTH_SHORT).show();
            Notification_Ngaythangnamsinhkhonghople();
        } else if (namdachon == namhientai) {
            if (thangdachon > thanghientai) {
                //sai
                Notification_Ngaythangnamsinhkhonghople();
            } else if (thangdachon == thanghientai) {
                if (ngaydachon >= ngayhientai) {
                    //sai
                    Notification_Ngaythangnamsinhkhonghople();
                } else {
                    //ok
                    attemptUpdate();
                }
            } else {
                //ok
                attemptUpdate();
            }
        } else {
            //ok
            attemptUpdate();
        }
    }
    private void Notification_saveThongtin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyHomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn cập nhật thông tin ?");
        builder.setNegativeButton("Tôi Đồng Ý !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
                dem=3;

                re1.setVisibility(View.INVISIBLE);
                lin1.setVisibility(View.INVISIBLE);
                progressBar1.setVisibility(View.VISIBLE);

                hanlHandlerUpdateThongtin.postDelayed(runnableUpdateThongtin,1000);

            }
        });
        builder.setPositiveButton("Tôi không chắc chắn !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_Ngaythangnamsinhkhonghople() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyHomeActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Ngày tháng năm sinh không hợp lệ!");
        builder.setMessage("Vui lòng nhập lại");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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



