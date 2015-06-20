package com.example.vinoth.test1;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.database.Cursor;

import java.util.*;
import java.io.*;
import android.graphics.*;

import android.widget.AdapterView.OnItemClickListener;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import com.bluejamesbond.text.DocumentView;


/**
 * Created by vinoth on 4/26/15.
 */
public class Menu1_fragment extends Fragment {
    View rootview;
    ArrayList<String> title= new ArrayList<String>();
    ArrayList<String> news= new ArrayList<String>();
    ArrayList<String> source= new ArrayList<String>();
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    int count=0;
    int i=0;
    Cursor c= null;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout,container,false);
        final DBConnection1 dbConnection1 = new DBConnection1(getActivity());
        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(getActivity());
        String tables[] = {"cnn","bbc","nytimes","engadget","verge","usa"};
        while(i<=5)
        {
            if(sharedPreferences.getString(tables[i],"").equalsIgnoreCase("true"))
            {
               c=dbConnection1.getvalues(tables[i]);
            }
            if(c!=null && c.moveToFirst())
            {
                do {
                    title.add(c.getString(0));
                    news.add(c.getString(1));
                    source.add(tables[i]);
                } while (c.moveToNext());
            }
            Log.w("Shared Pref11111111: ",(sharedPreferences.getString(tables[i],"")));

            i++;
        }
        news = new ArrayList<String>(new LinkedHashSet<String>(news));
        title = new ArrayList<String>(new LinkedHashSet<String>(title));
        ListAdapter listAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,title);
        ListView listView = (ListView) rootview.findViewById(R.id.list1);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                rootview = inflater.inflate(R.layout.details,container,false);
                getActivity().setContentView(R.layout.details);
                String title1 = String.valueOf(parent.getItemAtPosition(position));
                int pos = title.indexOf(title1);
                Log.w("Source: ",source.get(pos));
                Log.w("Title : ",title1);
                Log.w("News: ", news.get(pos));
                if(source.get(pos).equalsIgnoreCase("cnn") || source.get(pos).equalsIgnoreCase("bcc") || source.get(pos).equalsIgnoreCase("nytimes") || source.get(pos).equalsIgnoreCase("usa"))
                {
                    DocumentView tv = (DocumentView) getActivity().findViewById(R.id.detail);
                    tv.setText(news.get(pos));
                }
                else
                {
                    rootview = inflater.inflate(R.layout.detailsrss,container,false);
                    getActivity().setContentView(R.layout.detailsrss);
                    WebView webView= (WebView) getActivity().findViewById(R.id.wb);
                    webView.loadData(news.get(pos),"text/html", "UTF-8");
                }
            }
        });
        return rootview;
    }
}
