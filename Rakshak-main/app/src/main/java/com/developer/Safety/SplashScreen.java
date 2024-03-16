package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    boolean isAllPermissionsGranted = false;

  //  For fading text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadLoacle();
        requestPermission();



        SharedPreferences Firsttime = getSharedPreferences("FIRST", MODE_PRIVATE);
        String check = Firsttime.getString("FirstTime", "");

       findViewById(R.id.getstartBtn).setOnClickListener(view -> {
            if(isAllPermissionsGranted){

                //check for first time install or not
                if (check.equals("yes")) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    SplashScreen.this.finish();
                }
                else {
                startActivity(new Intent(SplashScreen.this,Declaration_Form.class));
                }
            }else {
                Toast.makeText(this, " Turn on Device Location ", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, " Please Grant Permissions! ", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
        });

        findViewById(R.id.VoiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllPermissionsGranted) {

                    //check for first time install or not
                    if (check.equals("yes")) {
                        startActivity(new Intent(SplashScreen.this, Voice.class));
                    } else {
                        startActivity(new Intent(SplashScreen.this, Declaration_Form.class));
                    }
                } else {
                   Toast.makeText(SplashScreen.this, " Turn on Device Location ", Toast.LENGTH_SHORT).show();
                   Toast.makeText(SplashScreen.this, " Please Grant Permissions! ", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
            }
        });

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


    private void requestPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECORD_AUDIO

                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            isAllPermissionsGranted = true;
                            requestLocation();
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }




    void requestLocation(){
        LocationRequest mLocationRequest = com.google.android.gms.location.LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                      //  startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(SplashScreen.this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

}