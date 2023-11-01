package com.example.anhvu.gioithieunhahangkhachsan;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_Tinnhan_gridview;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Tinnhan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class MenuSupportAdminActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Integer> arrayListMahopthoai;
    Adapter_Menu_Tinnhan_gridview adapter_menu_tinnhan_gridview;
    Intent intent,intentChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_support_admin);
        AnhXa();
        setAdapter();
        String url =urlpublic+"siteNhaHangvaKhachSan/read_tinnhan_layMAHOPTHOAI.php";
        laycsdlTinnhanGetMAHOPTHOAI(url);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentChat.putExtra("MAHT",arrayListMahopthoai.get(position));
                startActivity(intentChat);
            }
        });
        customActionBar();
    }
    private void AnhXa(){
        gridView=findViewById(R.id.grid_menu_support_admin);
        arrayListMahopthoai=new ArrayList<>();

        intent=getIntent();
        intentChat=new Intent(MenuSupportAdminActivity.this,MenuCHATSupportAdminActivity.class);
    }
    private void setAdapter(){
        adapter_menu_tinnhan_gridview=new Adapter_Menu_Tinnhan_gridview(this,arrayListMahopthoai);
        gridView.setAdapter(adapter_menu_tinnhan_gridview);
    }
    private void laycsdlTinnhanGetMAHOPTHOAI(String url){
        arrayListMahopthoai.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    arrayListMahopthoai.add(jsonObject.getInt("MAHOPTHOAI"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

            adapter_menu_tinnhan_gridview.notifyDataSetChanged();
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
