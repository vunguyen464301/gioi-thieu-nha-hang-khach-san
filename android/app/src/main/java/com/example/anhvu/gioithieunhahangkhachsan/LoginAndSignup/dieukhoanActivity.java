package com.example.anhvu.gioithieunhahangkhachsan.LoginAndSignup;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.anhvu.gioithieunhahangkhachsan.R;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class dieukhoanActivity extends AppCompatActivity {
    private Button btn_back;
    ActionBar actionBar;
    Intent intent;
    Animation thunho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieukhoan);

       //getActionBar().setTitle("heelo");
       // actionBar=getSupportActionBar();
       // actionBar.hide();
        btn_back=findViewById(R.id.btnBackdieukhoan);
        intent=getIntent();
        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);







        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                finish();
               // overridePendingTransition(R.anim.giunguyentuthe,R.anim.tuhiensangan);

            }
        });

    }
}
