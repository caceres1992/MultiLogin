package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView userprofile;
    SharedPreferences preferences;
    Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferences = getSharedPreferences("datos", MODE_PRIVATE);
        String resultado = preferences.getString("resultado", null);

        userprofile = findViewById(R.id.user_profile);


        userprofile.setText(resultado.toString());


        btn_close = findViewById(R.id.profile_logout);
        btn_close.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Cerrando session ", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(intent2);
        });

    }


}