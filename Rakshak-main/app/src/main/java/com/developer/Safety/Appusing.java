package com.developer.Safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class Appusing extends AppCompatActivity {
    WebView webView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate  (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appusing);


        loadLoacle();

        getWindow().setStatusBarColor(ContextCompat.getColor(Appusing.this,R.color.CommonStatusBar));
        webView = findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.youtube.com/embed/KNIkIC0jlk4?origin=https%3A%2F%2Fovdss.com%2Fapps%2Fyoutube-embedded-code-generator&enablejsapi=1&hl=Select%20Player%20Language");

        bottomNavigationView = findViewById(R.id.BottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.appusing);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(Appusing.this,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appusing:
                        return true;
                    case R.id.devCon:
                        startActivity(new Intent(Appusing.this,AboutUs.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.language:
                       changelanguage();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



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