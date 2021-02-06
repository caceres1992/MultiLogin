package com.example.myapplication.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ProfileActivity;
import com.example.myapplication.R;
//import com.firebase.ui.auth.AuthUI;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity   {
        SharedPreferences preferences;
    FirebaseAuth auth;

    LoginButton btn_login_Facebook;
    TextView username;
    CallbackManager mCallbackManager;

//    List<AuthUI.IdpConfig> provider = Arrays.asList(
//            new AuthUI.IdpConfig.GitHubBuilder().build(),
//            new AuthUI.IdpConfig.FacebookBuilder().build(),
//            new AuthUI.IdpConfig.MicrosoftBuilder().build(),
//            new AuthUI.IdpConfig.TwitterBuilder().build()
//
//    );

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("datos",MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
username = findViewById(R.id.username_facebook);
btn_login_Facebook = findViewById(R.id.login_button_para_facebook);
btn_login_Facebook.setReadPermissions("email","public_profile");


btn_login_Facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Log.d("Cacelado ", "facebook:onCancel");

    }

    @Override
    public void onError(FacebookException error) {
        Log.d("ERROR  ", "facebook:onError", error);

    }
});
//btn_login_Facebook.setOnClickListener(this::onClick);

// Initialize Facebook Login button





//        auth = FirebaseAuth.getInstance();
//        authStateListener = firebaseAuth -> {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//            if (user !=null){
//                Toast.makeText(getApplicationContext(),"iniciaste session " +user.getDisplayName(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent (getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("facebookName",user.getEmail());
//                startActivity(intent);
//                finish();
//            }else {
//                startActivityForResult(AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(provider)
//                        .setIsSmartLockEnabled(false)
//                        .build(),REQUEST_CODE
//                        );
//            }
//
//        };


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("token ", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"signInWithCredential:success"+task.getResult().getUser().getEmail(),Toast.LENGTH_LONG).show();
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);

                        SharedPreferences.Editor editor = preferences.edit()
                                .putString("resultado",task.getResult().getUser().getDisplayName());
                        editor.commit();
                        Intent intent = new Intent (getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }else {
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentuser = auth.getCurrentUser();
        updateUI(currentuser);
    }

    private void updateUI(FirebaseUser currentuser) {

    }
}