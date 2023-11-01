package com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Class_Table_Loaiban;

import com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage.Chitiet_Fragment_Homepage_2;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Loaiban;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Fragment_Activity_Homepage_2 extends Fragment {
    private View rootView;
    private ListView listView;
    private Context context;
    private ArrayList<Class_Table_Loaiban> arrayList;
    Adapter_Class_Table_Loaiban adapter_class_table_loaiban;
    Intent intentChitiet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_activity_homepage_2,container,false);
        AnhXa();
        clickListview();



        return rootView;
    }
    private void clickListview(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class_Table_Loaiban kh=arrayList.get(position);
                intentChitiet.putExtra("MA",kh.getMaloaiban());
                startActivity(intentChitiet);
                getActivity().overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
            }
        });
    }


    private void AnhXa(){
        listView=rootView.findViewById(R.id.listView_fragment_datban);
        arrayList=new ArrayList<Class_Table_Loaiban>();
        intentChitiet=new Intent(getActivity(), Chitiet_Fragment_Homepage_2.class);

        String url = urlpublic+ "siteNhaHangvaKhachSan/read_loaiban.php";
        laycsdl(url);
        adapter_class_table_loaiban =new Adapter_Class_Table_Loaiban(getActivity(),arrayList);
        listView.setAdapter(adapter_class_table_loaiban);
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
                                Class_Table_Loaiban cd;
                                cd = new Class_Table_Loaiban(jsonObject.getInt("MALOAIBAN"),
                                        jsonObject.getString("HINHCHUNG"),
                                        jsonObject.getString("TENBAN"),
                                        jsonObject.getInt("SOLUONG"));
                                arrayList.add(cd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter_class_table_loaiban.notifyDataSetChanged();
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
