package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.databinding.ActivityMainBinding;
import com.example.project.databinding.ActivityWelcomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FloatingActionButton floatingActionButton;

    BottomNavigationView bottomNavigationView;
    ActivityWelcomeBinding binding;

    ProgressDialog progressDialoglogout;


    //user profile
    FirebaseUser user;
    DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment()); //set home as default

        binding.navBottom.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.navBottomHome:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navBottomAccount:
                    replaceFragment(new ProfileFragment());
                    break;
                /*case R.id.navBottomOrder:
                    replaceFragment(new OrderFragment());
                    break;*/
                case R.id.navBottomClose:
                    exit(this);
                    break;
            }
            return true;
        });
        progressDialoglogout=new ProgressDialog(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        floatingActionButton = findViewById(R.id.fabAdd);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick();
            }
        });

        //remove background from bottom navigation bar
        bottomNavigationView = findViewById(R.id.navBottom);
        bottomNavigationView.setBackground(null);

        //user profile name
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        userID=user.getUid();

        final TextView name = (TextView) findViewById(R.id.finalName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //create user object
                User userProfile =snapshot.getValue(User.class);
                if (userProfile !=null){
                    String fullname = userProfile.fullname;
                    name.setText(fullname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WelcomeActivity.this,"Not able to get user data",Toast.LENGTH_LONG).show();
            }
        });



    }//end of onCreate

    //fab click
    private void fabClick() {
        openDrawer(drawerLayout);
    }

    //open frag method
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lytFrame,fragment);
        fragmentTransaction.commit();
    }
//backPress
    int counter=0;
    @Override
    public void onBackPressed() {
        counter++;
        if(counter<2){
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
        }
        else{

            exit(this);
        }

    }

    //Drawer
    //methods
    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void  ClickHome(View view){
        //recreate activity
        recreate();
    }
    public void ClickPrice(View view){
        //open market
      /*  replaceFragment(new MarketFragment());
        closeDrawer(drawerLayout);*/
    }
    public void ClickAccount(View view){
        //open account fragment
       replaceFragment(new ProfileFragment());
        closeDrawer(drawerLayout);
    }
    public void ClickOrder(View view){
        //open chat fragment
       /* replaceFragment(new OrderFragment());
        closeDrawer(drawerLayout);*/
    }

    public void ClickAboutUs(View view){
        //open about us fragment
        replaceFragment(new AboutFragment());
        closeDrawer(drawerLayout);
    }
    public void ClickHelp(View view){
        //open help fragment
        replaceFragment(new HelpFragment());
        closeDrawer(drawerLayout);
    }
    public void ClickLogOut(View view){
        //log out
        //initialize alert dialog
        AlertDialog.Builder builder0= new AlertDialog.Builder(WelcomeActivity.this);

        //set tittle
        builder0.setTitle("Logout");
        //set message
        builder0.setMessage("Sign out?");
        //positive yes button
        builder0.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialoglogout.setTitle("Signing you out");
                progressDialoglogout.setMessage("Please wait...");
                progressDialoglogout.show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                progressDialoglogout.dismiss();
            }
        });
        //negative NO button
        builder0.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss alert dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder0.show();

    }
    public void ClickExit(View view){
        //close app
        exit(this);
    }

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
 //for redirect activity currently not used here as am using fragments but may need it so i wont delete
    public static void redirectActivity(Activity activity,Class aClass) {
        //initiate intent
        Intent intent  =new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }

    
}

