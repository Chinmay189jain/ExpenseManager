package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class About extends AppCompatActivity {
    Window window;
    ImageView lnk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window=this.getWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if(Build.VERSION.SDK_INT>=21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        lnk=(ImageView)findViewById(R.id.linkedin);
        lnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.linkedin.com/in/chinmay-jain-a4a7351aa");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }
}