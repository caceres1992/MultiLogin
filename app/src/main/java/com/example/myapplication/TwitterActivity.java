package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.OAuthProvider;

public class TwitterActivity extends AppCompatActivity implements View.OnClickListener {
SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("datos",MODE_PRIVATE);
        setContentView(R.layout.activity_twitter);

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");

    }

    @Override
    public void onClick(View v) {

    }
}