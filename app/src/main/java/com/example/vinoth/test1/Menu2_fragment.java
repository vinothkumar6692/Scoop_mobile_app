package com.example.vinoth.test1;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by vinoth on 4/26/15.
 */
public class Menu2_fragment extends Fragment {
    View rootview;
    TextView tv;

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu2_layout,container,false);
        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(getActivity());
        String tables[] = {"cnn","bbc","nytimes","usa","verge","engadget"};
        while(i<=5)
        {
            if(sharedPreferences.getString(tables[i],"").equalsIgnoreCase("true"))
            {
                if(i==0)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb1);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else if(i==1)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb2);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else if (i==2)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb3);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else if (i==3)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb4);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else if (i==4)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb5);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else if (i==5)
                {
                    CheckBox checkBox = (CheckBox)rootview.findViewById(R.id.cb6);
                    checkBox.setChecked(!checkBox.isChecked());
                }
            }
            i++;

            }
         return rootview;
    }




}
