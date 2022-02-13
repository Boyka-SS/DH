package com.jinke.driverhealth.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/13
 */
public class MyFragmentAdapter  extends FragmentStateAdapter {
   private List<Fragment> list = new ArrayList<>();

    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle , List<Fragment> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}