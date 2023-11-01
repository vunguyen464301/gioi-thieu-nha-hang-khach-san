package com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Dichvu;
import com.example.anhvu.gioithieunhahangkhachsan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Chitiet_Fragment_Homepage_3 extends AppCompatActivity {
    private ImageView img1;
    private TextView txtma,txtten,txtchitiet;
    private Button btnBack;
    Intent intent;
    int strma;
    String strHinhchung,strten,strdongia;
    ArrayList<Class_Table_Dichvu> arrayList;
    Animation thunho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet__fragment__homepage_3);
        AnhXa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });
        laydulieutuIntent();
        customActionBar();

    }
    private void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_20));
        }
    }
    private void laydulieutuIntent(){
        //do tìm nhiều cách chưa gửi được hình giữa các activity dùng bitmap gì đó ,chưa nắm rõ
        //nên tạm thời dùng intent mã với tên của tâm hình rồi vô đường dẫn lấy luôn cho khỏe
        strHinhchung=intent.getStringExtra("HINHCHUNG").toString();
        strma=intent.getIntExtra("MA",0);
        strten=intent.getStringExtra("TENDICHVU").toString();
        strdongia=intent.getStringExtra("CHITIET");


        // String url =  "http://192.168.129.1/siteNhaHangvaKhachSan/read_menu_nhahang_where.php"+strma;
        //laycsdl(url);
        String url =  urlpublic+"siteNhaHangvaKhachSan/hinhanh/"+strHinhchung;
        Picasso.with(getApplicationContext()).load(url).into(img1);

        txtma.setText(strma+"");
        txtten.setText(strten+"");
        txtchitiet.setText(strdongia+"");




    }
    private void AnhXa(){
        img1=findViewById(R.id.img_chitiet_fragment_3);
        txtma=findViewById(R.id.txt1_chitiet_fragment_3);
        txtten=findViewById(R.id.txt2_chitiet_fragment_3);
        txtchitiet=findViewById(R.id.txt3_chitiet_fragment_3);
        btnBack=findViewById(R.id.btnBackChitietFragment3);
        intent =getIntent();
        arrayList=new ArrayList<Class_Table_Dichvu>();
        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
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
