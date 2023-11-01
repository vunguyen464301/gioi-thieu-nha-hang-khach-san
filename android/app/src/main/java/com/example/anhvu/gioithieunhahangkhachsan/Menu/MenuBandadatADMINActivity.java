package com.example.anhvu.gioithieunhahangkhachsan.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_BandadatAdmin;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datban;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class MenuBandadatADMINActivity extends AppCompatActivity {
    ListView listView;
    TextView txt1;
    ArrayList<Class_Table_Datban> arrayList;
    Adapter_Menu_BandadatAdmin adapter_menu_bandadatAdmin;

    ProgressBar progressBar;
    Animation phongto,thunho;
    int dem,demphong;
    private Handler handlerload=new Handler();
    private Runnable runnableload=new Runnable() {
        @Override
        public void run() {
            dem=dem-1;
            handlerload.postDelayed(runnableload,1000);
            if(dem==0){
                handlerload.removeCallbacks(runnableload);

                listView.setVisibility(View.VISIBLE);
                txt1.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                //kiểm tra phòng có tồn tại hay không
                //bởi vì set theo trong bảng datphong
                //từ bảng datphong nếu trùng makh thì kiếm tiếp trong phòng sẽ thấy
                if(arrayList.size()==0){
                    Notification_khongcobannaoduocdat();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bandadat_admin);

        AnhXa();
        customActionBar();
        txt1.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        listView.setAdapter(adapter_menu_bandadatAdmin);

        String url =urlpublic+"siteNhaHangvaKhachSan/read_datban.php";
        laycsdlbangDatban(url);

        dem=5;
        handlerload.postDelayed(runnableload,1000);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(MenuBandadatADMINActivity.this,view,position);
                return false;
            }
        });
    }
    private void AnhXa(){
        txt1=findViewById(R.id.txt1_menu_bandadat_Admin);
        listView=findViewById(R.id.listview_menu_bandadat_Admin);
        arrayList=new ArrayList<Class_Table_Datban>();
        adapter_menu_bandadatAdmin=new Adapter_Menu_BandadatAdmin(this,arrayList);

        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
        phongto= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongtomotxiu);
        progressBar=findViewById(R.id.load_progress_bandadat_Admin);


    }
    private void showPopupMenu(Activity act, View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(act, view);
        view.startAnimation(phongto);
        popupMenu.getMenuInflater().inflate(R.menu.menu_listview, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.huy:
                        Notification_xacnhanhuy(position);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
        popupMenu.show();
    }
    private void Notification_khongcobannaoduocdat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuBandadatADMINActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Không tồn tại");
        builder.setMessage("Không có bàn nào được đặt !");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Notification_xacnhanhuy(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuBandadatADMINActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Xác nhận hủy");
        builder.setMessage("Bạn có chắc chắn hủy không ?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Class_Table_Datban cd =arrayList.get(position);
                String urlcapnhattablephong =urlpublic+"siteNhaHangvaKhachSan/update_ban.php";
                String urldeletetabledatphong=urlpublic+"siteNhaHangvaKhachSan/delete_datban.php";
                String maphong=cd.getMaban();
                capnhatTableBan(urlcapnhattablephong,maphong);
                deleteTableDatban(urldeletetabledatphong,maphong);

                arrayList.remove(position);
                adapter_menu_bandadatAdmin.notifyDataSetChanged();

            }
        });
        builder.setPositiveButton("Tôi không chắc!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTableDatban(String url, final String maban){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(), "Hủy thành công !", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Hủy thất bại !", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params=new HashMap<>();
                params.put("MABAN",maban);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void capnhatTableBan(String url, final String maban){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("success")){
                            Toast.makeText(getApplicationContext(),"Cập nhật thành công !",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Cập nhật thất bại !",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("maban",maban);
                params.put("tinhtrang","Chưa đặt");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void laycsdlbangDatban(final String url)
    {
        arrayList.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Datban ban;
                        ban = new Class_Table_Datban(jsonObject.getString("MABAN"),
                                jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("GHICHU"));

                        arrayList.add(ban);

                        //  Toast.makeText(getApplicationContext(),"kich co :"+ arrayList.size()+"",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter_menu_bandadatAdmin.notifyDataSetChanged();
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
