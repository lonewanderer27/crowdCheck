package com.example.customchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class profileActivity extends AppCompatActivity {
    ImageButton profileBack;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profileBack = findViewById(R.id.profileBack);
        profileBack.setOnClickListener(view -> {
            Intent intent = new Intent(profileActivity.this, home.class);
            startActivity(intent);
        });

        logout = findViewById(R.id.toLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}