package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class LawsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);


        getWindow().setStatusBarColor(ContextCompat.getColor(LawsActivity.this,R.color.HelplineStatusBar));
      /*  MaterialButton MoreBtn;
        getWindow().setStatusBarColor(ContextCompat.getColor(LawsActivity.this,R.color.CommonStatusBar));
        findViewById(R.id.MoreBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://campcoder.blogspot.com/2023/03/womens-their-laws_21.html?m=1");
            }
        });
    }

    private void gotoUrl(String s){
        Uri uri =Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    */
    }
}