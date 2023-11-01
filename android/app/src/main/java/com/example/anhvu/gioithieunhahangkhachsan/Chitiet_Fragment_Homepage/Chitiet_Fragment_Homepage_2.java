package com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage;

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
import android.widget.AdapterView;
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
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Chitiet_Ban;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Ban;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datban;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Chitiet_Fragment_Homepage_2 extends AppCompatActivity {
    private Button btnBack;
    private ListView listView;
    int Maloaiban;
    Intent intent;
    Adapter_Chitiet_Ban adapter_chitiet_ban;
    ArrayList<Class_Table_Ban> arrayList;
    Animation thunho;
    int dem =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet__fragment__homepage_2);
        AnhXa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });
        String url = urlpublic+ "siteNhaHangvaKhachSan/read_ban_where.php?MALOAIBAN=" +String.valueOf(Maloaiban);
        laycsdlBanonMySQL(url);
        customActionBar();
    }
    private void laycsdlBanonMySQL(String url)
    {
        arrayList.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Ban ban;
                        ban = new Class_Table_Ban(jsonObject.getString("MABAN"),
                                jsonObject.getString("MOTA"),
                                jsonObject.getInt("MALOAIBAN"),
                                jsonObject.getString("TINHTRANG")

                             );
                       arrayList.add(ban);
                      //  Toast.makeText(getApplicationContext(),arrayList.size()+" kich co ",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //cap nhat lai adapter
                adapter_chitiet_ban.notifyDataSetChanged();
                listView.setAdapter(adapter_chitiet_ban);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    //Lấy csdl bảng datban có điều kiện where để kiểm tra xem bàn đó có ai đặt không !


    private void customActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.homepage_20));
        }



    }
    private void AnhXa(){
        btnBack=findViewById(R.id.btnBackChitietFragment2);
        listView=findViewById(R.id.listView_fragment_datban_chitiet);

        intent=getIntent();
        Maloaiban =intent.getIntExtra("MA",0);

        arrayList =new ArrayList<Class_Table_Ban>();
        adapter_chitiet_ban=new Adapter_Chitiet_Ban(this,arrayList,Chitiet_Fragment_Homepage_2.this);
        listView.setAdapter(adapter_chitiet_ban);
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
