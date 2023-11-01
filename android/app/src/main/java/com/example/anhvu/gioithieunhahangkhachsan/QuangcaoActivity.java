package com.example.anhvu.gioithieunhahangkhachsan;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.vitrihinhquangcao;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.demhinh;
public class QuangcaoActivity extends AppCompatActivity {
    Button btnClose;
    FrameLayout fram;
    ImageView img;
    Intent intent;
    int timeLimit=0;
    private Handler handler =new Handler();
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,500);
            if(timeLimit==0){
                fram.setBackgroundResource(R.drawable.homepage_17);
            }else{
                fram.setBackgroundResource(R.drawable.homepage_18);
            }
            timeLimit=timeLimit+1;
            if(timeLimit==2){
                timeLimit=0;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quangcao);
        AnhXa();
        handler.postDelayed(runnable,1000);
        //Toast.makeText(getApplicationContext(),"Vi tri hinh quang cao "+vitrihinhquangcao +" dem hinh :" +demhinh,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"hinh" +intent.getStringExtra("hinhanh"),Toast.LENGTH_SHORT).show();

        String url = urlpublic+ "siteNhaHangvaKhachSan/hinhanh/"+intent.getStringExtra("hinhanh");
        Picasso.with(QuangcaoActivity.this).load(url).into(img);

        if(vitrihinhquangcao==demhinh-1){
           // Toast.makeText(getApplicationContext(),"da toi max",Toast.LENGTH_SHORT).show();
            vitrihinhquangcao=-1;
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                //Toast.makeText(getApplicationContext(),"tắt liền",Toast.LENGTH_SHORT).show();
                intent.putExtra("quangcao",true);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.thunhotu1xuong0);
            }
        });
    }

    private void AnhXa(){
        btnClose=findViewById(R.id.btnClose_quangcao);
        fram=findViewById(R.id.fram_quangcao);
        img=findViewById(R.id.img1_quangcao);
        intent=getIntent();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        handler.removeCallbacks(runnable);
        //Toast.makeText(getApplicationContext(),"tắt liền",Toast.LENGTH_SHORT).show();
        intent.putExtra("quangcao",true);
        setResult(RESULT_OK,intent);
        finish();
        overridePendingTransition(R.anim.giunguyentuthe,R.anim.thunhotu1xuong0);
    }
}
