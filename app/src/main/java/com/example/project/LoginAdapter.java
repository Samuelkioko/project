package com.example.project;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment tab=new LoginTabFragment();
                return tab;
            case 1:
                SignupTabFragment tab2=new SignupTabFragment();
                return tab2;
            default:
                return null;

        }
}

    @Override
    public int getCount() {
        return totalTabs;

    }
}
