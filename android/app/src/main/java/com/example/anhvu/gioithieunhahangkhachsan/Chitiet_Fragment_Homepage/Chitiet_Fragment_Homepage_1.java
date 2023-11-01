package com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Chitiet_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Chitiet_Fragment_Homepage_1 extends AppCompatActivity {
    ListView listView;
    Button btnBack;
    Intent intent;
    int maloaiphong;
    //lưu ý nhỏ ,cho dù set đến thế nào thì nó vẫn hoàn lại như cũ ,vì thế nếu set thì set luôn arraylist để nó hiện thị lại
    ArrayList<Class_Table_Phong> arrayList;
    Adapter_Chitiet_Phong adapter_chitiet_phong;
    Animation thunho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet__fragment__homepage_1);
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

        String url = urlpublic+ "siteNhaHangvaKhachSan/read_phong_where.php?MALOAIPHONG="+String.valueOf(maloaiphong);
        laycsdlPhongonMySQL(url);

        customActionBar();

    }
    private void laydulieutuIntent(){
        maloaiphong=intent.getIntExtra("MALOAIPHONG",0);
       // Toast.makeText(getApplicationContext(),maloaiphong+" ",Toast.LENGTH_SHORT).show();
    }
    private void AnhXa(){
        listView=findViewById(R.id.listView_fragment_datphong_chitiet);
        btnBack=findViewById(R.id.btnBackChitietFragment1);
        intent=getIntent();

        arrayList=new ArrayList<Class_Table_Phong>();
        adapter_chitiet_phong=new Adapter_Chitiet_Phong(this,arrayList,Chitiet_Fragment_Homepage_1.this);
        listView.setAdapter(adapter_chitiet_phong);
        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
    }
    private void laycsdlPhongonMySQL(String url)
    {
        arrayList.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Phong phong;
                        phong = new Class_Table_Phong(jsonObject.getString("MAPHONG"),
                                jsonObject.getString("MOTA"),
                                jsonObject.getString("HINHANH"),
                                jsonObject.getInt("MALOAIPHONG"),
                                jsonObject.getInt("GIA1GIO"),
                                jsonObject.getInt("DONGIANGAY"),
                                jsonObject.getInt("DONGIADEM"),
                                jsonObject.getInt("DONGIANGAYDEM"),
                                jsonObject.getString("TIENNGHI"),
                                jsonObject.getString("TINHTRANG"));
                        arrayList.add(phong);

                       // Toast.makeText(getApplicationContext(),arrayList.size()+" ",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter_chitiet_phong.notifyDataSetChanged();
                    listView.setAdapter(adapter_chitiet_phong);
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
