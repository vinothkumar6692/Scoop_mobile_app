package com.example.vinoth.test1;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import 	java.net.HttpURLConnection;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


import java.io.*;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    private SignInButton signinButton;
    private ImageView image;
    private TextView username, emailLabel;
    private LinearLayout profileFrame, signinFrame;
    static SQLiteDatabase db1;
    SQLiteDatabase db;
    String rst;
    int count = 0;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i=0,count=0;
        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(this);
        String tables[] = {"cnn","bbc","nytimes","engadget","verge","usa"};
        while(i<=5)
        {
            if (!sharedPreferences.getString(tables[i], "").equalsIgnoreCase("false"))
            {
                count++;
                Log.w("Shared Pref: ", sharedPreferences.getString(tables[i],""));
            }
            i++;
        }
        if(count == 6)
        {
            editor = sharedPreferences.edit();
            editor.putString("cnn","true");
            editor.putString("bbc","true");
            editor.putString("nytimes","true");
            editor.putString("engadget","true");
            editor.putString("verge","true");
            editor.putString("usa","true");
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected() || mMobile.isConnected())
        {
            setContentView(R.layout.activity_main);
            DBConnection db = new DBConnection();
            db.execute();
            signinButton = (SignInButton) findViewById(R.id.signin);
            signinButton.setOnClickListener(this);
            image = (ImageView) findViewById(R.id.image);
            signinFrame = (LinearLayout) findViewById(R.id.signinFrame);


        }
        else
        {
            Log.w("Not connected","");
            setContentView(R.layout.menu1_layout);
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            MainActivity.this.startActivity(intent);
        }

    }

    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError()
    {
        if (mConnectionResult.hasResolution())
        {
            try
            {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            }
            catch (IntentSender.SendIntentException e)
            {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
            if (!result.hasResolution()) {
                GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
                return;
            }

            if (!mIntentInProgress) {
                // store mConnectionResult
                mConnectionResult = result;

                if (signedInUser) {
                    resolveSignInError();
                }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    if (responseCode == RESULT_OK) {
                        signedInUser = false;

                    }
                    mIntentInProgress = false;
                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                    break;
            }
        }

        @Override
        public void onConnected(Bundle arg0) {
            signedInUser = false;
            Log.w("Connected","");
            getProfileInformation();
           DBConnection dbConnection = new DBConnection();
             dbConnection.execute();
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            MainActivity.this.startActivity(intent);
        }

        private void updateProfile(boolean isSignedIn) {
            if (isSignedIn) {
                signinFrame.setVisibility(View.GONE);
                profileFrame.setVisibility(View.VISIBLE);

            } else {
                signinFrame.setVisibility(View.VISIBLE);
                profileFrame.setVisibility(View.GONE);
            }
        }

        private void getProfileInformation() {
            try {
                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                    String personName = currentPerson.getDisplayName();

                Toast.makeText(this, "Hello,"+personName +"!", Toast.LENGTH_LONG).show();
                    String personPhotoUrl = currentPerson.getImage().getUrl();
                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    int gender = currentPerson.getGender();
                    Person.AgeRange age= currentPerson.getAgeRange();
                    String age1 = age.toString();
                
                Log.w("Username: ",personName);
                Log.w("email: ",email);
                Log.w("Age: ",age1);
                Log.w("Gender: ", Integer.toString(gender));
                    updateProfile(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionSuspended(int cause) {
            mGoogleApiClient.connect();
            updateProfile(false);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signin:
                    googlePlusLogin();
                    break;
            }
        }

        public void signIn(View v) {
            googlePlusLogin();
        }

        public void logout(View v) {
            googlePlusLogout();
        }

        private void googlePlusLogin() {
            if (!mGoogleApiClient.isConnecting()) {
                signedInUser = true;
                resolveSignInError();
            }
        }

        public void googlePlusLogout() {
            if (mGoogleApiClient.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
                updateProfile(false);
            }
        }

        // download Google Account profile image, to complete profile
        private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
            ImageView downloadedImage;

            public LoadProfileImage(ImageView image) {
                this.downloadedImage = image;
            }

            protected Bitmap doInBackground(String... urls) {
                String url = urls[0];
                Bitmap icon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return icon;
            }

            protected void onPostExecute(Bitmap result) {
                downloadedImage.setImageBitmap(result);
            }
        }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public class DBConnection extends AsyncTask<Void, Void, Integer> {


        @Override
        protected Integer doInBackground(Void... params) {

            try {
                String result1 = "Database connection success\n";
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connected");
                Connection con = DriverManager.getConnection("jdbc:mysql://twitterheatmap.cjkuojdbhc2z.us-east-1.rds.amazonaws.com:3306/twitter", "raghav", "raghavabc");
                System.out.println("Databaseection success");

                db = openOrCreateDatabase("newsdb", MODE_PRIVATE, null);
                db.execSQL("create table if not exists news1(title varchar(150), news text, time datetime)");
                db.execSQL("create table if not exists usa(title varchar(150), news text, time datetime)");
                db.execSQL("create table if not exists verge(title varchar(150), news text, time datetime)");
                db.execSQL("create table if not exists engadget(title varchar(150), news text, time datetime)");
                System.out.println("\n Table Created!!!");
                db.execSQL("delete from news1");
                db.execSQL("delete from usa");
                db.execSQL("delete from verge");
                db.execSQL("delete from engadget");
                System.out.print("Deletion !!!!!");

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from new_york_time_headlines_api order by time desc limit 20");

                while (rs.next()) {
                    String rs1 = rs.getString(1).replaceAll("'","");
                    String rs2 = rs.getString(2).replaceAll("'","");
                    Log.w("Helllooo: ","Value added !!!!");
                    String rs3 = rs.getString(3).replaceAll("'","");
                    db.execSQL("insert into news1 values ('" + rs1 + "','" + rs2 + "','" + rs3 + "')");
                }
                rs.close();

                ResultSet rs2= st.executeQuery("select * from usa_today_headlines_api order by time desc limit 20");
                while(rs2.next()) {
                    String rs1 = rs2.getString(1).replaceAll("'","");
                    String rs22 = rs2.getString(2).replaceAll("'","");
                    String rs3 = rs2.getString(3).replaceAll("'","");
                    db.execSQL("insert into usa values ('" + rs1 + "','" + rs22 + "','" + rs3 + "')");
                }
                rs2.close();

                ResultSet rs3= st.executeQuery("select * from the_verge_android order by time desc limit 20");
                while(rs3.next()) {
                    String rs1 = rs3.getString(1).replaceAll("'","");
                    String rs22 = rs3.getString(2).replaceAll("'","");
                    String rs33 = rs3.getString(3).replaceAll("'","");
                    db.execSQL("insert into verge values ('" + rs1 + "','" + rs22 + "','" + rs33 + "')");
                }
                System.out.print("Inserted 333333!!!!!");
                rs3.close();

                ResultSet rs4= st.executeQuery("select * from the_verge_apple order by time desc limit 20");
                while(rs4.next()) {
                    String rs1 = rs4.getString(1).replaceAll("'","");
                    String rs22 = rs4.getString(2).replaceAll("'","");
                    String rs33 = rs4.getString(3).replaceAll("'","");
                    db.execSQL("insert into verge values ('" + rs1 + "','" + rs22 + "','" + rs33 + "')");
                }
                rs4.close();

                ResultSet rs5= st.executeQuery("select * from the_verge_mobile order by time desc limit 20");
                while(rs5.next()) {
                    String rs1 = rs5.getString(1).replaceAll("'","");
                    String rs22 = rs5.getString(2).replaceAll("'","");
                    String rs33 = rs5.getString(3).replaceAll("'","");
                    db.execSQL("insert into verge values ('" + rs1 + "','" + rs22 + "','" + rs33 + "')");
                }
                rs5.close();

                ResultSet rs6= st.executeQuery("select * from engadget_feed order by time desc limit 20");
                while(rs6.next()) {
                    String rs1 = rs6.getString(1).replaceAll("'","");
                    String rs22 = rs6.getString(2).replaceAll("'","");
                    String rs33 = rs6.getString(3).replaceAll("'","");
                    db.execSQL("insert into engadget values ('" + rs1 + "','" + rs22 + "','" + rs33 + "')");
                    Log.w("Inserted engadget","");
                }
                rs6.close();
                 System.out.print("Inserted!!!!!");
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //tv.setText(e.toString());
            }
            return count;

        }

        protected void onPostExecute(Integer rst) {
        }

    }
}