package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LandingActivity extends AppCompatActivity {
    Button startApp;
    TextView appName, motto, power, devname, org;

    float v=0;
    ImageView imageView, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        startApp=findViewById(R.id.btnGetstarted);
        appName=findViewById(R.id.txtAppName);
        motto=findViewById(R.id.txtMotto);
        power=findViewById(R.id.txtPower);
        org=findViewById(R.id.txtHitech);
        devname=findViewById(R.id.txtDev);
        imageView=findViewById(R.id.imgLogo);
        imageView2=findViewById(R.id.imgHitech);

        //animate
        startApp.setTranslationY(300);
        appName.setTranslationY(300);
        imageView.setTranslationY(300);
        motto.setTranslationY(300);
        power.setTranslationY(300);
        devname.setTranslationY(300);
        org.setTranslationY(300);
        imageView2.setTranslationY(300);

        startApp.setAlpha(v);
        appName.setAlpha(v);
        imageView.setAlpha(v);
        motto.setAlpha(v);
        power.setAlpha(v);
        devname.setAlpha(v);
        org.setAlpha(v);
        imageView2.setAlpha(v);

        imageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        appName.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        motto.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        power.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1200).start();
        imageView2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1400).start();
        org.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1600).start();
        devname.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1800).start();
        startApp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

        //set onclick listeners
        startApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }//end of oncreate


    //open customer
    private void openLogin() {
        Intent intent = new Intent(LandingActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }//end of customer


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