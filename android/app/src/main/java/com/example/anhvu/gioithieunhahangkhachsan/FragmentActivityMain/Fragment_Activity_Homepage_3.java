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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.anhvu.gioithieunhahangkhachsan.Adapter.Adapter_Class_Table_Dichvu;
import com.example.anhvu.gioithieunhahangkhachsan.Chitiet_Fragment_Homepage.Chitiet_Fragment_Homepage_3;
import com.example.anhvu.gioithieunhahangkhachsan.Class.Class_Table_Dichvu;
import com.example.anhvu.gioithieunhahangkhachsan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Fragment_Activity_Homepage_3 extends Fragment{
    private View rootView;
    private ListView listView;
    private ArrayList<Class_Table_Dichvu> arrayList;
    private Adapter_Class_Table_Dichvu adapter_class_table_dichvu;
    private Context context;
    Intent intentChitiet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_activity_homepage_3,container,false);
        AnhXa();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class_Table_Dichvu kh =arrayList.get(position);

                intentChitiet.putExtra("HINHCHUNG",kh.getHinhchung());
                intentChitiet.putExtra("MA",kh.getMadichvu());
                intentChitiet.putExtra("TENDICHVU",kh.getTendichvu());
                intentChitiet.putExtra("CHITIET",kh.getChitiet());
                startActivity(intentChitiet);
                getActivity().overridePendingTransition(R.anim.chuyentubenphaisangbentrai,R.anim.giunguyentuthe);
            }
        });

        return rootView;
    }
    private void AnhXa(){
        listView=rootView.findViewById(R.id.listView_fragment_dichvu);
        arrayList=new ArrayList<Class_Table_Dichvu>();
        intentChitiet =new Intent(getActivity(), Chitiet_Fragment_Homepage_3.class);

        String url = urlpublic+ "siteNhaHangvaKhachSan/read_dichvu.php";
        laycsdl(url);
        adapter_class_table_dichvu =new Adapter_Class_Table_Dichvu(getActivity(),arrayList);
        listView.setAdapter(adapter_class_table_dichvu);



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
                                Class_Table_Dichvu cd;
                                cd = new Class_Table_Dichvu(jsonObject.getInt("MADICHVU"),
                                        jsonObject.getString("HINHCHUNG"),
                                        jsonObject.getString("TENDICHVU"),
                                        jsonObject.getString("CHITIET"));
                                arrayList.add(cd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter_class_table_dichvu.notifyDataSetChanged();
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
   /*
    void datalistview(){
        String[] data ={"Android","WindowsPhone","IOS","BlackBerry","WindowsPhone","WindowsPhone","WindowsPhone"};
        //bỏ dữ liệu vào ArrayAdapter
        ArrayAdapterphone(data);
    }
    void ArrayAdapterphone(String []data){
        //tạo 1 arrâydapter chứa dữ liệu trên list view
        ArrayAdapter<String> phone=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,data){
            @NonNull
            @Override
            //chỉ cần gõ getView là nó gợi ý ra với điều kiện phải ở trong ngoặc của ArrayAdapter
            //phần này chỉnh màu chữ
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=super.getView(position,convertView,parent);
                TextView text=view.findViewById(android.R.id.text1);
                //sét màu chữ thành màu trắng
                text.setTextColor(Color.RED);
                //trả về bảng danh sách
                return view;
            }
        };
        //bỏ dữ liệu mảng vào phone ,bỏ phone vào adapter ,list view sẽ lấy trong adapter
        listView.setAdapter(phone);
    }
    */

}
