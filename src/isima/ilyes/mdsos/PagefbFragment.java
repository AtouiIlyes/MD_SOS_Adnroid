package isima.ilyes.mdsos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import info.androidhive.slidingmenu.*;

public class PagefbFragment extends Fragment{

public PagefbFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_pagefb, container, false);
         
        return rootView;
    }
}