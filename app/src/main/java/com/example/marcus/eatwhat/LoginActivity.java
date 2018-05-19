package com.example.marcus.eatwhat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button btnfblogin = findViewById(R.id.btnfblogin);
        final ProgressBar circleload = findViewById(R.id.circleload);
        circleload.setVisibility(View.GONE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();

        // Initialize Official Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton officialbtnfblogin = findViewById(R.id.officialbtnfblogin);
        officialbtnfblogin.setReadPermissions("email", "public_profile");
        officialbtnfblogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                btnfblogin.setVisibility(View.INVISIBLE);
                circleload.setVisibility(View.VISIBLE);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
                btnfblogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
                btnfblogin.setVisibility(View.VISIBLE);

            }
        });

        // Initialize Unofficial Facebook Login button
        btnfblogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                officialbtnfblogin.callOnClick();
            }
        });

    }

    private void logintohome(){
        Intent logintohome2 = new Intent (LoginActivity.this, MainActivity.class);
        startActivity(logintohome2);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() { //called when leaving and entering application
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            logintohome();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            String user_id = mAuth.getCurrentUser().getUid();
                            final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                            current_user_db.addListenerForSingleValueEvent(new ValueEventListener() { //Check if user exist
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        // use "username" already exists
                                        logintohome();
                                    } else {
                                        // User does not exist, create profile then login
                                        Map newuser = new HashMap();
                                        newuser.put("Name","Newbie");
                                        newuser.put("Age","");
                                        newuser.put("Gender","");
                                        newuser.put("Location","");
                                        newuser.put("Type","");
                                        current_user_db.setValue(newuser);
                                        logintohome();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }




}
