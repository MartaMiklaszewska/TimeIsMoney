package com.example.timeismoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextInputEditText displayEditText;
    Button updateButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        circleImageView = findViewById(R.id.circleImageView);
        displayEditText = findViewById(R.id.displayEditText);
        updateButton = findViewById(R.id.updateProfileButton);
        progressBar = findViewById(R.id.progressBar);

    }

    public void updateProfile(View view) {
    }
}
