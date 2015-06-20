package com.example.vinoth.test1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by vinoth on 4/26/15.
 */
public class Menu4_fragment extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.logout,container,false);
        Toast.makeText(getActivity(),"Logged out!",Toast.LENGTH_LONG).show();
        return rootview;
    }
}
