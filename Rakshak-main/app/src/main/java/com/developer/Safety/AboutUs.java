package com.developer.Safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class AboutUs extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        loadLoacle();
        getWindow().setStatusBarColor(ContextCompat.getColor(AboutUs.this,R.color.CommonStatusBar));


        bottomNavigationView = findViewById(R.id.BottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.devCon);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appusing:
                        startActivity(new Intent(AboutUs.this,Appusing.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.devCon:
                        return true;
                    case R.id.language:
                       changelanguage();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });







        findViewById(R.id.Whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+919905991685";
                Uri uri = Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", number));
                Intent entent = new Intent(Intent.ACTION_VIEW);
                entent.setData(uri);
                entent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(entent);
            }
        });
        findViewById(R.id.Facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fblink = "https://www.facebook.com/profile.php?id=100014179291685";
                String fbpackage = "com.facebook.katana";
                String fbweb = "https://www.facebook.com/rishav.anand.9883";

                openLink(fblink, fbpackage, fbweb);

            }
        });
        findViewById(R.id.Instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fblink = "https://www.instagram.com/__rishav_anand?igsh=MzRlODBiNWFlZA==";
                String fbpackage = "com.instagram.android";
                openLink(fblink, fbpackage, fblink);
            }
        });

    }



    private void openLink(String fblink,String fbpackage,String fbweb){
        try {
            Uri uri =Uri.parse(fblink);
            Intent entent=new Intent(Intent.ACTION_VIEW);
            entent.setData(uri);
            entent.setPackage(fbpackage);
            entent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(entent);
        }catch (ActivityNotFoundException activityNotFoundException){
            Uri uri=Uri.parse(fbweb);
            Intent entent=new Intent(Intent.ACTION_VIEW);
            entent.setData(uri);
            entent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(entent);
        }
    }








    private void changelanguage(){
        final String language[] = {"English","Hindi"};
        AlertDialog.Builder mBuilder =new AlertDialog.Builder(this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (which==0){
                    setLocale("");
                    recreate();
                } else if (which==1) {
                    setLocale("hi");
                    recreate();
                }
            }
        });
        mBuilder.create();
        mBuilder.show();
    }


    private void setLocale(String language){
        Locale locale=new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration =new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("app_lang",language);
        editor.apply();

    }
    private void loadLoacle(){
        SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
        String language = preferences.getString("app_lang","");
        setLocale(language);
    }


}
