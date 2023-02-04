package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class Introductory extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        //lottie initialization
        lottieAnimationView=findViewById(R.id.lottie);


        //make lottie to slide vertically downwards
        lottieAnimationView.animate().translationY(1400).setDuration(3000).setStartDelay(4000);

        //next activity
        Thread thread=new Thread(){
            public void run(){
                try{
                    Thread.sleep(4000); //4 seconds delay
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(Introductory.this, LandingActivity.class); //open login
                    startActivity(intent);
                }
            }
        };

        thread.start();

    }//end of oncreate

    //on back press
    @Override
    public void onBackPressed() {

        exit(this);
    }

    //exit
    public static void exit(Activity activity) {
        //initialize alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        //set tittle
        builder.setTitle("Exit");
        //set message
        builder.setMessage("Close app?");
        //positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity

                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        //negative NO button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss alert dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }
}