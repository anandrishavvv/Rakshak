package com.developer.Safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FusedLocationProviderClient fusedLocationClient;
    String myLocation = "";
    Context context;
    SmsManager manager = SmsManager.getDefault();
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    SettingsContentObserver settingsContentObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       loadLoacle();
        bottomNavigationView = findViewById(R.id.BottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.appusing:
                        startActivity(new Intent(MainActivity.this,Appusing.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.devCon:
                        startActivity(new Intent(MainActivity.this,AboutUs.class));
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


        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.MainStatusBar));
        //Instructions Dialog
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean start = prefs.getBoolean("start", true);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCancelable(false);

        if(start) {
         dialog.show();
        }

        Button Btnok = dialog.findViewById(R.id.Btnok);

        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("start",false);
                editor.apply();
                dialog.dismiss();

            }
        });



        settingsContentObserver = new SettingsContentObserver(this, new Handler());

        getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.
                CONTENT_URI, true, settingsContentObserver);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        findViewById(R.id.panicBtn).setOnClickListener(this);
        findViewById(R.id.fourth).setOnClickListener(this);
        findViewById(R.id.first).setOnClickListener(this);
        findViewById(R.id.second).setOnClickListener(this);
        findViewById(R.id.third).setOnClickListener(this);
        findViewById(R.id.HelplineBtn).setOnClickListener(this);
        findViewById(R.id.callBtn).setOnClickListener(this);
        findViewById(R.id.instruction).setOnClickListener(this);
        // for register complaint website redirect
        findViewById(R.id.complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Complaint.class));
            }
        });
    }

    private void gotoUrl(String s){
        Uri uri =Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.first) {
            startActivity(new Intent(MainActivity.this, ContactActivity.class));
        } else if (id == R.id.second) {
            startActivity(new Intent(MainActivity.this, SmsActivity.class));
        } else if (id == R.id.third) {
            startActivity(new Intent(MainActivity.this, LawsActivity.class));
        } else if (id == R.id.fourth) {
            startActivity(new Intent(MainActivity.this, SelfDefenseActivity.class));
        } else if (id == R.id.HelplineBtn) {
            startActivity(new Intent(MainActivity.this,Helpline.class));
        } else if (id == R.id.panicBtn) {
            int num = 100;
            Intent empcall = new Intent(Intent.ACTION_CALL);
            empcall.setData((Uri.parse("tel:" + num)));
            startActivity(empcall);
        } else if (id == R.id.instruction) {
            // updateToPolice();
            // Toast.makeText(this, "This Is Proposed Button To be available once Approved.", Toast.LENGTH_LONG).show();
             startActivity(new Intent(MainActivity.this,Voice.class));
        } else if (id == R.id.callBtn) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            location.getAltitude();
                            location.getLongitude();
                            myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                        } else {
                            myLocation = "Unable to Find Location :(";
                        }
                        sendmessage();
                        call();
                    });
        }
    }

    // this is for future purpose
    void updateToPolice(){
        String Num ="";
        manager.sendTextMessage(Num,null,"Sir Please Help Me ! \n This is my Location :\n "+myLocation,null,null);
    }
    void sendmessage() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number")) {
            String NUM = (sharedPreferences.getString("number", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }
    void sendmessage1() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number1")) {
            String NUM = (sharedPreferences.getString("number1", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n Please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }
    void sendmessage2() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number2")) {
            String NUM = (sharedPreferences.getString("number2", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n Please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }

    void call() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number")) {
            String calling = (sharedPreferences.getString("number", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }
    void call1() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number1")) {
            String calling = (sharedPreferences.getString("number1", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }
    void call2() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number2")) {
            String calling = (sharedPreferences.getString("number2", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }

    // volume button listener
    public class SettingsContentObserver extends ContentObserver {
        int previousVolume;
        Context context;
        SettingsContentObserver(Context c, Handler handler) {
            super(handler);
            context = c;
            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            previousVolume =
                    Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume =
                    Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC);
            int delta = previousVolume - currentVolume;
            if (delta > 1) {
                Toast.makeText(MainActivity.this, " Volume Decreased \n Calling", Toast.LENGTH_SHORT).show();
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(location -> {
                            if (location != null) {
                                location.getAltitude();
                                location.getLongitude();
                                myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                            } else {
                                myLocation = "Unable to Find Location :(";
                            }
                            sendmessage2();
                        });
                call2();
                previousVolume = currentVolume;
            }
            else if (delta < -1) {
                Toast.makeText(MainActivity.this, "Volume Increased \n Calling", Toast.LENGTH_SHORT).show();
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(location -> {
                            if (location != null) {
                                location.getAltitude();
                                location.getLongitude();
                                myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                            } else {
                                myLocation = "Unable to Find Location :(";
                            }
                            sendmessage1();
                        });
                call1();
                previousVolume = currentVolume;
            }
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