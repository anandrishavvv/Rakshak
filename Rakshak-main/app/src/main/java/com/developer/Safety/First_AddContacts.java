package com.developer.Safety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class First_AddContacts extends AppCompatActivity {

    SmsManager manager = SmsManager.getDefault();
    Button save,change;
    EditText num,num1,num2,name;
    String n= "82073144512";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_add_contacts);
        name=findViewById(R.id.username);
        num = findViewById(R.id.num);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        save = findViewById(R.id.save);
       change = findViewById(R.id.changelanguage);

       changelanguage();
      loadLoacle();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changelanguage();
            }
        });


        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);

        getWindow().setStatusBarColor(ContextCompat.getColor(First_AddContacts.this,R.color.CommonStatusBar));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n1 = num.getText().toString().trim();
                String n2 = num1.getText().toString().trim();
                String n3 = num2.getText().toString().trim();
                String Username = name.getText().toString().trim();

                if (Username.isEmpty()){
                    name.setError("this field can'nt be Empty");
                }if (n1.isEmpty()){
                   num.setError("this field can'nt be Empty");
                }if (n2.isEmpty()){
                    num1.setError("this field can'nt be Empty");
                }if (n3.isEmpty()){
                    num2.setError("this field can'nt be Empty");
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("number", num.getText().toString());
                    editor.putString("number1", num1.getText().toString());
                    editor.putString("number2", num2.getText().toString());
                    editor.putString("username",name.getText().toString());
                    editor.apply();

                    Toast.makeText(First_AddContacts.this, "Saved", Toast.LENGTH_LONG).show();
                    manager.sendTextMessage(n1,null," I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You.",null,null);
                    manager.sendTextMessage(n2,null," I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You." ,null,null);
                    manager.sendTextMessage(n3,null," I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You.",null,null);
                  //  manager.sendTextMessage(n,null," I have Installed :- "+Username,null,null);
                    /// This is for one time show code
                    SharedPreferences Firsttime = getSharedPreferences("FIRST", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = Firsttime.edit();
                    editor1.putString("FirstTime", "yes");
                    editor1.apply();
                    startActivity(new Intent(First_AddContacts.this, MainActivity.class));
                }
                }
        });

    }

    private void changelanguage(){
            final String language[] = {"English","Hindi"};
        AlertDialog.Builder mBuilder =new AlertDialog.Builder(this);
        mBuilder.setTitle("Choose App Language");
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