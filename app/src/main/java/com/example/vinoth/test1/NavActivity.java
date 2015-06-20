package com.example.vinoth.test1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.CheckBox;
import android.widget.Toast;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class NavActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    SQLiteDatabase db;


    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.putString("cnn","true");
        editor.putString("bbc","true");
        editor.putString("nytimes","true");
        editor.putString("engadget","true");
        editor.putString("verge","true");
        editor.putString("usa","true");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the
        // drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = null;
        switch(position){
            case 0:
                objFragment = new Menu1_fragment();
                break;
            case 1:
                objFragment = new Menu2_fragment();
                break;
            case 2:
                objFragment = new Menu4_fragment();
                break;
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.nav, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    public void opensettings(){
      Toast.makeText(this,"Settings button pressed",Toast.LENGTH_SHORT).show();
    }
    public void opensearch(){
        Toast.makeText(this,"Search button pressed",Toast.LENGTH_SHORT).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_nav, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, NavActivity.class));
        // finish();
        // moveTaskToBack(true);
    }



    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        switch(view.getId()) {
            case R.id.cb1:
                if (checked)
                    editor.putString("cnn","true");
                else
                    editor.putString("cnn","false");

                break;
            case R.id.cb2:
                if (checked)
                    editor.putString("bbc","true");
                else
                    editor.putString("bbc","false");
                // Cheese me
                break;

            case R.id.cb3:
                if (checked)
                    editor.putString("nytimes","true");
                else
                    editor.putString("nytimes","false");
                // Cheese me
                break;
            case R.id.cb4:
                if (checked)
                    editor.putString("usa","true");
                else
                    editor.putString("usa","false");
                // Cheese me
                break; case R.id.cb5:
                if (checked)
                    editor.putString("verge","true");
                else
                    editor.putString("verge","false");
                // Cheese me
                break; case R.id.cb6:
                if (checked)
                    editor.putString("engadget","true");
                else
                    editor.putString("engadget","false");
                // Cheese me
                break;
            // TODO: Veggie sandwich
        }
        editor.apply();
    }
}
