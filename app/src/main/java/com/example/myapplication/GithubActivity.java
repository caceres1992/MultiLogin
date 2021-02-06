package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GithubActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    EditText usuario;
    Button btn_inciar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("datos", MODE_PRIVATE);
        setContentView(R.layout.activity_github);

        usuario = findViewById(R.id.git_username);
        btn_inciar = findViewById(R.id.git_iniciar);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_inciar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(usuario.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
        } else {
            SignInWithGithubProvider(
                    OAuthProvider.newBuilder("github.com")
                            .addCustomParameter("login", usuario.getText().toString())
                            .setScopes(

                                    new ArrayList<String>() {
                                        {
                                            add("user:email");
                                        }
                                    }
                            )
                            .build()
            );
        }
    }

    private void SignInWithGithubProvider(OAuthProvider login) {
        Task<AuthResult> pendingAuthTask = firebaseAuth.getPendingAuthResult();
        if (pendingAuthTask != null) {
            pendingAuthTask.addOnSuccessListener(authResult -> {
                Toast.makeText(getApplicationContext(), "User Exist " + authResult.getAdditionalUserInfo().getUsername(), Toast.LENGTH_LONG).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            });
        } else {
            firebaseAuth.startActivityForSignInWithProvider(this, login).addOnFailureListener(
                    e -> {
                        Toast.makeText(getApplicationContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
            )
                    .addOnSuccessListener(authResult -> {

                        SharedPreferences.Editor editor = preferences.edit()
                                .putString("resultado", authResult.getAdditionalUserInfo().getUsername());
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    });
        }
    }
}
