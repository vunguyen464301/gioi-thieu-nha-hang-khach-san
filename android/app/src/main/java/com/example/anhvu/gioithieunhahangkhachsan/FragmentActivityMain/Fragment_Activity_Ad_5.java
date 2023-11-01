package com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anhvu.gioithieunhahangkhachsan.Interface.Callback_setOnclick;
import com.example.anhvu.gioithieunhahangkhachsan.R;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Fragment_Activity_Ad_5 extends Fragment {
    private View rootView;
    private TextView txtRight,txtLeft;
    private Callback_setOnclick callback_setOnclick;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_activity_ad_5,container,false);
        AnhXa();
        callback_setOnclick= (Callback_setOnclick) getActivity();
        txtLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback_setOnclick.setOnclick(3);
            }
        });
        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback_setOnclick.setOnclick(0);
            }
        });

        return rootView;
    }
    private void AnhXa(){
        txtRight=rootView.findViewById(R.id.clickRIGHT5);
        txtLeft=rootView.findViewById(R.id.clickLEFT5);
    }
}
