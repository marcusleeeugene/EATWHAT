package com.example.marcus.eatwhat;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private FirebaseAuth mAuth;
    Button btnfblogout;
    TextView profilename;
    final Map location = new HashMap();
    final Map type = new HashMap();


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnfblogout = getView().findViewById(R.id.btnfblogout);
        profilename = getView().findViewById(R.id.profilename);
        final CheckBox cbnorth = getView().findViewById(R.id.cbnorth);
        final CheckBox cbsouth = getView().findViewById(R.id.cbsouth);
        final CheckBox cbeast = getView().findViewById(R.id.cbeast);
        final CheckBox cbwest = getView().findViewById(R.id.cbwest);
        final CheckBox cbchinese = getView().findViewById(R.id.cbchinese);
        final CheckBox cbmalay = getView().findViewById(R.id.cbmalay);
        final CheckBox cbindian = getView().findViewById(R.id.cbindian);
        final CheckBox cbjapanese = getView().findViewById(R.id.cbjapanese);
        final CheckBox cbwestern = getView().findViewById(R.id.cbwestern);
        final CheckBox cbfastfood = getView().findViewById(R.id.cbfastfood);

        FacebookSdk.sdkInitialize(getApplicationContext());

        //Set profile name to surfing capital font
        Typeface surfingcapitalfont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Surfing Capital.ttf");
        profilename.setTypeface(surfingcapitalfont);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        final DatabaseReference current_user_db_location = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Location");
        final DatabaseReference current_user_db_type = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Type");
        final DatabaseReference current_user_db_name = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Name");

        // Initialize Unofficial Facebook Log out button
        btnfblogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logout();
            }
        });

        current_user_db_name.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                //childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                //mDatabase.updateChildren(childUpdates);
                profilename.setText(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Default check database and check location and type checkboxes without trigerring OnCheckedChangeListeners
        cbnorth.setTag(false);
        cbsouth.setTag(false);
        cbeast.setTag(false);
        cbwest.setTag(false);
        cbchinese.setTag(false);
        cbmalay.setTag(false);
        cbindian.setTag(false);
        cbjapanese.setTag(false);
        cbwestern.setTag(false);
        cbfastfood.setTag(false);



        cbnorth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                current_user_db_location.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbnorth.isChecked()){
                            location.put("Location", snapshot.getValue() + "," + cbnorth.getText().toString());
                            current_user_db.updateChildren(location);
                        }
                        else{

                            location.put("Location", snapshot.getValue().toString().replace("," + cbnorth.getText().toString(), ""));
                            current_user_db.updateChildren(location);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbsouth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_location.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbsouth.isChecked()){
                            location.put("Location", snapshot.getValue() + "," + cbsouth.getText().toString());
                            current_user_db.updateChildren(location);
                        }
                        else{

                            location.put("Location", snapshot.getValue().toString().replace("," + cbsouth.getText().toString(), ""));
                            current_user_db.updateChildren(location);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbeast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_location.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbeast.isChecked()){
                            location.put("Location", snapshot.getValue() + "," + cbeast.getText().toString());
                            current_user_db.updateChildren(location);
                        }
                        else{

                            location.put("Location", snapshot.getValue().toString().replace("," + cbeast.getText().toString(), ""));
                            current_user_db.updateChildren(location);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbwest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_location.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbwest.isChecked()){
                            location.put("Location", snapshot.getValue() + "," + cbwest.getText().toString());
                            current_user_db.updateChildren(location);
                        }
                        else{

                            location.put("Location", snapshot.getValue().toString().replace("," + cbwest.getText().toString(), ""));
                            current_user_db.updateChildren(location);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbchinese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbchinese.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbchinese.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbchinese.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbmalay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbmalay.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbmalay.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbmalay.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbindian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbindian.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbindian.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbindian.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbjapanese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbchinese.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbjapanese.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbjapanese.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbwestern.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbwestern.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbwestern.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbwestern.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        cbfastfood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current_user_db_type.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // do some stuff once
                        System.out.println(snapshot.getValue());
                        if(cbfastfood.isChecked()){
                            type.put("Type", snapshot.getValue() + "," + cbfastfood.getText().toString());
                            current_user_db.updateChildren(type);
                        }
                        else{

                            type.put("Type", snapshot.getValue().toString().replace("," + cbfastfood.getText().toString(), ""));
                            current_user_db.updateChildren(type);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private void logout(){
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        Toast.makeText(getActivity(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
        Intent gotologin = new Intent(getActivity(), LoginActivity.class);
        gotologin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotologin);
        getActivity().finish();
    }


}
