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
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_PhongdadatAdmin;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datphong;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;

public class MenuPhongdadatADMINActivity extends AppCompatActivity {
    ListView listView;
    TextView txt1;
    ArrayList<Class_Table_Datphong> arrayList;
    Adapter_Menu_PhongdadatAdmin adapter_menu_phongdadatAdmin;
    ProgressBar progressBar;
    Animation phongto,thunho;
    int dem;
    private Handler handlerload=new Handler();
    private Runnable runnableload=new Runnable() {
        @Override
        public void run() {
            dem=dem-1;
            handlerload.postDelayed(runnableload,1000);
            if(dem==0){
                handlerload.removeCallbacks(runnableload);

                txt1.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                //kiểm tra phòng có tồn tại hay không
                //bởi vì set theo trong bảng datphong
                //từ bảng datphong nếu trùng makh thì kiếm tiếp trong phòng sẽ thấy
                if(arrayList.size()==0){
                    Notification_khongcophongnaoduocdat();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_phongdadat_admin);

        AnhXa();
        txt1.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        listView.setAdapter(adapter_menu_phongdadatAdmin);
        String url =urlpublic+"siteNhaHangvaKhachSan/read_datphong.php";
        laycsdlbangDatphong(url);

        dem=5;
        handlerload.postDelayed(runnableload,1000);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(MenuPhongdadatADMINActivity.this,view,position);
                return false;
            }
        });
        customActionBar();
    }
    private void AnhXa(){
        txt1=findViewById(R.id.txt1_menu_phongdadat_Admin);
        listView=findViewById(R.id.listview_menu_phongdadat_Admin);
        arrayList=new ArrayList<Class_Table_Datphong>();
        adapter_menu_phongdadatAdmin=new Adapter_Menu_PhongdadatAdmin(this,arrayList);

        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);
        phongto=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongtomotxiu);
        progressBar=findViewById(R.id.load_progress_phongdadat_Admin);


    }
    private void showPopupMenu(Activity act, View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(act, view);
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
    private void capnhatTablePhong(String url, final String maphong) {
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
                params.put("maphong", maphong);
                //set lại là chưa đặt vì hủy thì chỉ có cách vô danh sách phòng để đặt lại
                params.put("tinhtrang", "Chưa đặt");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void deleteTableDatphong(String url, final String maphong) {
        //phải dùng maxphong ,không được dùng makh vì khi xóa thì chỉ xóa phòng đó ,còn kh có nhiều phòng nhưng 1 phòng chỉ 1 khách hàng
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Hủy thành công !", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Hủy thất bại !", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: " + volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MAPHONG", maphong);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void laycsdlbangDatphong(String url){
        arrayList.clear();
        RequestQueue requestQueue = new Volley().newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Class_Table_Datphong phong;
                        phong = new Class_Table_Datphong(jsonObject.getString("MAPHONG"),
                                jsonObject.getInt("MAKHACHHANG"),
                                jsonObject.getString("GHICHU"));

                        arrayList.add(phong);
                   } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter_menu_phongdadatAdmin.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void Notification_khongcophongnaoduocdat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPhongdadatADMINActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Không tồn tại");
        builder.setMessage("Không có phòng nào được đặt !");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPhongdadatADMINActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Xác nhận hủy");
        builder.setMessage("Bạn có chắc chắn hủy không ?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Class_Table_Datphong cd =arrayList.get(position);
                String urlcapnhattablephong =urlpublic+"siteNhaHangvaKhachSan/update_phong.php";
                String urldeletetabledatphong=urlpublic+"siteNhaHangvaKhachSan/delete_datphong.php";
                String maphong=cd.getMaphong();
                capnhatTablePhong(urlcapnhattablephong,maphong);
                deleteTableDatphong(urldeletetabledatphong,maphong);

                arrayList.remove(position);
                adapter_menu_phongdadatAdmin.notifyDataSetChanged();
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
