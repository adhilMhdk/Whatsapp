package com.whatsappclone.activitys.mainSession;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.whatsappclone.activitys.mainSession.mainFragments.CallFragment;
import com.whatsappclone.activitys.mainSession.mainFragments.CameraFragment;
import com.whatsappclone.activitys.mainSession.mainFragments.ChatFragment;
import com.whatsappclone.activitys.mainSession.mainFragments.StatusFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public ViewPagerAdapter(@NonNull FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new CameraFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new StatusFragment();
            case 3:
                return new CallFragment();
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Camera";
            case 1:
                return "Chat";
            case 2:
                return "Status";
            case 3:
                return "Call";
            default:
                return null;
        }
    }
}
