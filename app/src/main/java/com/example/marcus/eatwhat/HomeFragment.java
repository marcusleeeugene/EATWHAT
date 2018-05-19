package com.example.marcus.eatwhat;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static Button btnspin;
    public static Button btnpromo;
    public static LinearLayout promoad;
    public static Uri uri;
    public static Button btnclosead;
    public static TextView label;
    public static ImageView imagebox;
    public static ImageView colourwheel;
    public static ImageView trianglepointer;
    public static BottomNavigationView btmmenunav;
    long animationDuration = 10200; //milliseconds
    MediaPlayer mp = new MediaPlayer();


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Required empty public constructor
        btnspin = (Button) getView().findViewById(R.id.btnspin);
        label = (TextView) getView().findViewById(R.id.label);
        imagebox = (ImageView) getView().findViewById(R.id.imagebox);
        colourwheel = (ImageView) getView().findViewById(R.id.colourwheel);
        trianglepointer = (ImageView) getView().findViewById(R.id.trianglepointer);
        promoad = (LinearLayout) getView().findViewById(R.id.promoad);
        btnclosead = (Button) getView().findViewById(R.id.btnclosead);
        btnpromo = (Button) getView().findViewById(R.id.btnpromo);
        Typeface surfingcapitalfont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Surfing Capital.ttf");
        label.setTypeface(surfingcapitalfont);
        btmmenunav = (BottomNavigationView) getView().findViewById(R.id.btmmenunav);

        promoad.setVisibility(View.GONE);
        btnclosead.setVisibility(View.GONE);

        imagebox.setVisibility(View.INVISIBLE);
        label.setVisibility(View.INVISIBLE);
        btnpromo.setVisibility(View.INVISIBLE);
        MediaPlayer mp = new MediaPlayer();


        btnspin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                btnspin.setVisibility(View.GONE);
                promoad.setVisibility(View.GONE);
                btnclosead.setVisibility(View.INVISIBLE);
                imagebox.setVisibility(View.INVISIBLE);
                label.setVisibility(View.INVISIBLE);
                btnpromo.setVisibility(View.INVISIBLE);
                //Start Spin Sound
                playspinsound();
                //Draw eatingplace data from GoogleSheet
                fetchdata process = new fetchdata();
                process.execute();
                colourwheel.setVisibility(View.VISIBLE);
                trianglepointer.setVisibility(View.VISIBLE);
                //Start Spin Wheel Animation
                handleAnimation(colourwheel);
            }
        });


        btnpromo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                promoad.setVisibility(View.VISIBLE);
                btnclosead.setVisibility(View.VISIBLE);
                btnpromo.setVisibility(View.INVISIBLE);
            }
        });

        promoad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnclosead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                promoad.setVisibility(View.GONE);
                btnclosead.setVisibility(View.INVISIBLE);
                btnpromo.setVisibility(View.VISIBLE);
            }
        });
    }

    int randomWithRange(int min, int max) //randomise number of rotations for colourwheel
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }


    public void playspinsound(){
        mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.spinwheel);
        mp.start();
    }
    @Override
    public void onStop() { //releases playspinsound once media finishes playing to prevent garbage data colelction
        super.onStop();
        mp.release();
    }


    public void handleAnimation(View view){ //Rotate colour wheel
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(colourwheel,"rotation", 0f, randomWithRange(4680,5040)); //1800
        rotateAnimation.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) { //When Animation ends, do the following:
                colourwheel.setVisibility(View.INVISIBLE);
                trianglepointer.setVisibility(View.INVISIBLE);
                imagebox.setVisibility(View.VISIBLE);
                label.setVisibility(View.VISIBLE);
                if(fetchdata.promovalidity.equals("VALID")){
                    btnpromo.setVisibility(View.VISIBLE);
                }
                btnspin.setVisibility(View.VISIBLE);
            }
        });
    }

}
