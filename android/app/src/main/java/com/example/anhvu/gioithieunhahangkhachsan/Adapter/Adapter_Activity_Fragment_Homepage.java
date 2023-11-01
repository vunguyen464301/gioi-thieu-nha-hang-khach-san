package com.example.anhvu.gioithieunhahangkhachsan.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain.Fragment_Activity_Homepage_1;
import com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain.Fragment_Activity_Homepage_2;
import com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain.Fragment_Activity_Homepage_3;
import com.example.anhvu.gioithieunhahangkhachsan.FragmentActivityMain.Fragment_Activity_Homepage_4;
import static com.example.anhvu.gioithieunhahangkhachsan.MainActivity.urlpublic;
public class Adapter_Activity_Fragment_Homepage extends FragmentStatePagerAdapter {
    private String listTab[] = {"","","",""};
    private Fragment_Activity_Homepage_1 mFirstFragment;
    private Fragment_Activity_Homepage_2 mSecondFragment;
    private Fragment_Activity_Homepage_3 mThirdFragment;
    private Fragment_Activity_Homepage_4 mFourFragment;



    public Adapter_Activity_Fragment_Homepage(FragmentManager fm) {
        super(fm);
        mFirstFragment = new Fragment_Activity_Homepage_1();
        mSecondFragment = new Fragment_Activity_Homepage_2();
        mThirdFragment =new Fragment_Activity_Homepage_3();
        mFourFragment =new Fragment_Activity_Homepage_4();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mFirstFragment;
        } else if (position == 1) {
            return mSecondFragment;
        } else if(position ==2){
            return  mThirdFragment;
        } else if(position ==3){
            return  mFourFragment;
        }
        else{

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}