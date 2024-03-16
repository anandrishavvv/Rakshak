package com.developer.Safety;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ContactActivity extends AppCompatActivity {

    SmsManager manager = SmsManager.getDefault();
    Button save,show;
    EditText num,num1,num2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        num = findViewById(R.id.num);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        save = findViewById(R.id.save);
        show = findViewById(R.id.show);

        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);

        getWindow().setStatusBarColor(ContextCompat.getColor(ContactActivity.this,R.color.CommonStatusBar));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n1 = num.getText().toString().trim();
                String n2 = num1.getText().toString().trim();
                String n3 = num2.getText().toString().trim();

                if (n1.isEmpty()) {
                    num.setError("this field can'nt be Empty");
                }
                if (n2.isEmpty()) {
                    num1.setError("this field can'nt be Empty");
                }
                if (n3.isEmpty()) {
                    num2.setError("this field can'nt be Empty");
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("number", num.getText().toString());
                    editor.putString("number1", num1.getText().toString());
                    editor.putString("number2", num2.getText().toString());
                    editor.apply();
                    Toast.makeText(ContactActivity.this, "Saved", Toast.LENGTH_LONG).show();
                    manager.sendTextMessage(n1,null,"I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You.",null,null);
                    manager.sendTextMessage(n2,null,"I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You.",null,null);
                    manager.sendTextMessage(n3,null,"I added to you\nAs My Emergency Contact. You will receive a message with my location info in an Emergency By Rakshak App.\n So Take it Seriously , Thank You.",null,null);
                    num.setText("");
                    num1.setText("");
                    num2.setText("");

                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.contains("number")) {
                    num.setText(sharedPreferences.getString("number", ""));
                }
                if (sharedPreferences.contains("number1")) {
                    num1.setText(sharedPreferences.getString("number1", ""));
                }
                if (sharedPreferences.contains("number2")) {
                    num2.setText(sharedPreferences.getString("number2", ""));
                }
            }
        });
    }

}