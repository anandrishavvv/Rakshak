package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Declaration_Form extends AppCompatActivity {

    Button Privacy;
    TextView Privacy2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_form);

       Privacy = findViewById(R.id.PrivacyPolicy);
       Privacy2 = findViewById(R.id.PrivacyPolicy2);

        getWindow().setStatusBarColor(ContextCompat.getColor(Declaration_Form.this,R.color.teal_700));
       Privacy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             startActivity(new Intent(Declaration_Form.this,First_AddContacts.class));
           }
       });

       Privacy2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Declaration_Form.this,First_AddContacts.class));
           }
       });

    }
}