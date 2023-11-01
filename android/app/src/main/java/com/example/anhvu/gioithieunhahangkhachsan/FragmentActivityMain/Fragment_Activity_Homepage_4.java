package com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Class_Table_Menu_Nhahang;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Menu_Tinnhan_Admin;
import com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage.Chitiet_Fragment_Homepage_4;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Menu_Nhahang;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Fragment_Activity_Homepage_4 extends Fragment {
    private View rootView;
    private ListView listView;
    private ArrayList<Class_Table_Menu_Nhahang> arrayList;
    private Adapter_Class_Table_Menu_Nhahang adapter_class_table_menu_nhahang;
    private Context context;
    private TextView txt;
    Intent intentChitiet;
    private Animation hiensangan;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_activity_homepage_4, container, false);
        AnhXa();





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Class_Table_Menu_Nhahang kh =arrayList.get(position);
                intentChitiet.putExtra("HINHCHUNG",kh.getHinhchung());
                intentChitiet.putExtra("MAMON",kh.getMamon());
                intentChitiet.putExtra("TENMONAN",kh.getTenmonan());
                intentChitiet.putExtra("DONGIA",kh.getDongia());
                startActivity(intentChitiet);
                getActivity().overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
            }
        });


        return rootView;
    }


    private void AnhXa(){
        listView=rootView.findViewById(R.id.listView_fragment_menunhahang);
        arrayList =new ArrayList<Class_Table_Menu_Nhahang>();
        intentChitiet=new Intent(getActivity(), Chitiet_Fragment_Homepage_4.class);

        String url = urlpublic+ "siteNhaHangvaKhachSan/read_menu_nhahang.php";
        laycsdl(url);
        adapter_class_table_menu_nhahang =new Adapter_Class_Table_Menu_Nhahang(getActivity(),arrayList);
        listView.setAdapter(adapter_class_table_menu_nhahang);


    }
    private void laycsdl(String url){
        RequestQueue requestQueue =new Volley().newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Class_Table_Menu_Nhahang cd;
                                cd = new Class_Table_Menu_Nhahang(jsonObject.getInt("MAMON"),
                                        jsonObject.getString("HINHCHUNG"),
                                        jsonObject.getString("TENMONAN"),
                                        jsonObject.getInt("DONGIA"));
                                arrayList.add(cd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter_class_table_menu_nhahang.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context.getApplicationContext(), "Loi: " + volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
