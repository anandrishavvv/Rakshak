package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Helpline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        getWindow().setStatusBarColor(ContextCompat.getColor(Helpline.this,R.color.HelplineStatusBar));


        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "06122294301";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "06122294303";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "06122294163";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "9431822967";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "06122214318";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "06122547843";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });

        findViewById(R.id.seven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "01126942369";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num ="0612256480";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
        findViewById(R.id.nine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = "9431820406";
                Intent empcall = new Intent(Intent.ACTION_CALL);
                empcall.setData((Uri.parse("tel:" + num)));
                startActivity(empcall);
            }
        });
    }

}