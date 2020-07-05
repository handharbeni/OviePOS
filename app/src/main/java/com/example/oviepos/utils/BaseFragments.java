package com.example.oviepos.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oviepos.R;

public class BaseFragments extends Fragment {
    public Activity getActivityInstance(Context context){
        Activity a = null;
        if (context instanceof Activity){
            a=(Activity) context;
        }
        return a;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getAttributes().windowAnimations = R.style.fragmentSlide;
    }
}
