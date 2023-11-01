package com.example.anhvu.gioithieunhahangkhachsan.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_Phongdadat;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Datphong;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Phong;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.khachhangtamthoi;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class MenuPhongdadatActivity extends AppCompatActivity {
    Intent intent;
    ListView listView;
    Adapter_Menu_Phongdadat adapter_menu_phongdadat;
    ArrayList<Class_Table_Phong> arrayListPhong;
    ArrayList<Class_Table_Datphong> arrayListDatphong;
    Button btnBack;
    ProgressBar progressBar;
    Animation phongto,thunho;
    RelativeLayout re1,re2;
    int dem,demphong;
    private Handler handlerload=new Handler();
    private Runnable runnableload=new Runnable() {
        @Override
        public void run() {
            dem=dem-1;
            handlerload.postDelayed(runnableload,1000);
            if(dem==0){
                handlerload.removeCallbacks(runnableload);

                re1.setVisibility(View.VISIBLE);
                re2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                //kiểm tra phòng có tồn tại hay không
                //bởi vì set theo trong bảng datphong
                //từ bảng datphong nếu trùng makh thì kiếm tiếp trong phòng sẽ thấy
                if(arrayListPhong.size()==0){
                    Notification_khongcophongnaoduocdat();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_phongdadat);
        AnhXa();
        re1.setVisibility(View.INVISIBLE);
        re2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        //đầu tiên là lấy makh trong bảng datphong ,xong rồi lấy được mã phòng trong bảng datphong ,lấy mã phòng
        //đem qua bảng phòng rồi kiếm hiển thị thông tin
        String urldatphong =urlpublic+ "siteNhaHangvaKhachSan/read_datphong_whereMAKH.php?MAKHACHHANG=" + khachhangtamthoi.get(0).getMakhachhang() + "";
        laycsdlbangDatphongwhereMAKH(urldatphong);
        dem=5;
        handlerload.postDelayed(runnableload,1000);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //đăng kí menu ngữ cảnh cho listview
                //registerForContextMenu(listView);
                showPopupMenu(MenuPhongdadatActivity.this, view,position);
                return false;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(thunho);
                finish();
                overridePendingTransition(R.anim.giunguyentuthe,R.anim.chuyentubentraisangbenphai);
            }
        });

        customActionBar();
    }

    private void showPopupMenu(Activity act, final View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(act, view);
        view.startAnimation(phongto);
        popupMenu.getMenuInflater().inflate(R.menu.menu_listview, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.huy:
                        //view.startAnimation(phongto);
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


    //cái này là menu ngữ cảnh kiểu xuất ra menu ở giữa màn hình
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_listview,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.huy:
                Toast.makeText(getApplicationContext(),"Sap huy !",Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }

        return super.onContextItemSelected(item);
    }
    */
    private void laycsdlbangDatphongwhereMAKH(String url){
        arrayListDatphong.clear();
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

                        arrayListDatphong.add(phong);
                      //  Toast.makeText(getApplicationContext(),"kich co: "+arrayListDatphong.size()+" ",Toast.LENGTH_SHORT).show();

                        //  Toast.makeText(context.getApplicationContext(),ban.getMaban()+" "+ban.getMakhachhang(),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=0;i<arrayListDatphong.size();i++){
                    String ma =arrayListDatphong.get(i).getMaphong();
                    //String str ="\""+ma+"\"";
                    String urlphong=urlpublic+"/siteNhaHangvaKhachSan/read_phong_whereMAPHONG.php?MAPHONG="+ma;
                    laycsdlbangPhong(urlphong);
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
    private void laycsdlbangPhong(String url){
        //arrayListPhong.clear();
        //do là lấy danh sách phòng where maphong nên  ko được phép clear
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
                        arrayListPhong.add(phong);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter_menu_phongdadat.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Loi: "+ volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void AnhXa(){
        listView =findViewById(R.id.listview_menu_phongdadat);
        btnBack=findViewById(R.id.btn_backMenuPhongdadat);
        intent =getIntent();
        arrayListPhong =new ArrayList<Class_Table_Phong>();
        arrayListDatphong=new ArrayList<Class_Table_Datphong>();
        adapter_menu_phongdadat = new Adapter_Menu_Phongdadat(this,arrayListPhong);
        listView.setAdapter(adapter_menu_phongdadat);

        phongto= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.phongtomotxiu);
        thunho= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.thunhodimotxiu);

        progressBar=findViewById(R.id.load_progress_menu_phongdadat);
        re1=findViewById(R.id.reMenuPhongdadat);
        re2=findViewById(R.id.re1MenuPhongdadat);
    }
    private void Notification_khongcophongnaoduocdat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPhongdadatActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPhongdadatActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Xác nhận hủy");
        builder.setMessage("Bạn có chắc chắn hủy không ?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Class_Table_Phong cd =arrayListPhong.get(position);
                String urlcapnhattablephong =urlpublic+"siteNhaHangvaKhachSan/update_phong.php";
                String urldeletetabledatphong=urlpublic+"siteNhaHangvaKhachSan/delete_datphong.php";
                String maphong=cd.getMaphong();
                capnhatTablePhong(urlcapnhattablephong,maphong);
                deleteTableDatphong(urldeletetabledatphong,maphong);

                arrayListPhong.remove(position);
                adapter_menu_phongdadat.notifyDataSetChanged();
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
