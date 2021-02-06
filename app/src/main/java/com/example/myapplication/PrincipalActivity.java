package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnFacebook, btn_Github, btn_microsoft, btn_twitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        btn_Github = findViewById(R.id.bt_loginGithub);
        btnFacebook = findViewById(R.id.loginFacebook);
        btn_twitter = findViewById(R.id.btn_twitter);
        btn_microsoft = findViewById(R.id.btn_Login_Microsoft);

        btnFacebook.setOnClickListener(this::onClick);
        btn_Github.setOnClickListener(this::onClick);
        btn_twitter.setOnClickListener(this::onClick);
        btn_microsoft.setOnClickListener(this::onClick);
//
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        Button b = (Button) v;
        switch (b.getId()) {
            case R.id.bt_loginGithub:
                intent = new Intent(getApplicationContext(), GithubActivity.class);
                startActivity(intent);
                break;
            case R.id.loginFacebook:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_twitter:
                intent = new Intent(getApplicationContext(), GmailActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Login_Microsoft:
                Toast.makeText(getApplicationContext(), "estamos microsoft", Toast.LENGTH_LONG).show();
                break;

        }
    }
}