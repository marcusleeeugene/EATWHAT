package com.example.marcus.eatwhat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity{

    public static BottomNavigationView btmmenunav;
    final Fragment homefragment = new HomeFragment();
    final Fragment settingsfragment = new SettingsFragment();
    final FragmentManager transaction = getSupportFragmentManager();
    Fragment active = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up all fragments to be add and show and hide when told to do so to prevent fragments from reloading when requested
        transaction.beginTransaction().add(R.id.MainLayout, settingsfragment,"settings").commit();
        transaction.beginTransaction().add(R.id.MainLayout, homefragment, "home").commit();
        active = homefragment;


        btmmenunav = findViewById(R.id.btmmenunav);
        btmmenunav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.menu_home:{
                        if(active != homefragment) {
                            transaction.beginTransaction().show(homefragment).commit();
                        }
                        else{
                            transaction.beginTransaction().hide(active).show(homefragment).commit();
                            active = homefragment;
                        }
                        return true;
                    }

                    case R.id.menu_settings: {
                        if (active != homefragment) {
                            transaction.beginTransaction().show(settingsfragment).commit();
                        } else {
                            transaction.beginTransaction().hide(active).show(settingsfragment).commit();
                            active = homefragment;
                        }
                        return true;
                    }
                }
                return true;
            }
        });
    }

}
