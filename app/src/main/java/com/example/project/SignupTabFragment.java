package com.example.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class  SignupTabFragment extends Fragment {
    Button signup;
    EditText fullname,email,phoneNumber,county_of_res, campus_of_study, who_im_i;
    TextInputEditText password1,password2;
    FloatingActionButton fabGoogle,fabFb,fabPhone;

    FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    AutoCompleteTextView countyAutoCompleteTextView;
    AutoCompleteTextView campusAutoCompleteTextView;
    AutoCompleteTextView roleAutoCompleteTextView;

    String[] County_Names;
    String[] Campus_Names;
    String[] Role_Names;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.signup_tab_fragment, container,false);

        signup=root.findViewById(R.id.btnSignUp);
        fullname=root.findViewById(R.id.edtName);
        email=root.findViewById(R.id.edtEmailSignUp);
        county_of_res=root.findViewById(R.id.edtCounty);
        campus_of_study=root.findViewById(R.id.edtCampus);
        who_im_i=root.findViewById(R.id.edtRole);
        phoneNumber=root.findViewById(R.id.edtPhone);
        password1=root.findViewById(R.id.edtPassword1SignUp);
        password2=root.findViewById(R.id.edtPassword2SignUp);


        fabGoogle=root.findViewById(R.id.fab_google);
        fabFb=root.findViewById(R.id.fab_facebook);
        fabPhone=root.findViewById(R.id.fab_phone);

        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getActivity());

        //autocomplete county
        countyAutoCompleteTextView=root.findViewById(R.id.edtCounty);
        County_Names=getResources().getStringArray(R.array.county);
        //array adapter to add the values to the textview, create layout and specify resource with the string
        ArrayAdapter<String> countyAdapter =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, County_Names);
        //add the adapter to autocompletetextview
        countyAutoCompleteTextView.setAdapter(countyAdapter);

        //autocomplete campus
        campusAutoCompleteTextView=root.findViewById(R.id.edtCampus);
        Campus_Names=getResources().getStringArray(R.array.campus);
        ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Campus_Names);
        campusAutoCompleteTextView.setAdapter(campusAdapter);

        //autocomplete role
        roleAutoCompleteTextView=root.findViewById(R.id.edtRole);
        Role_Names=getResources().getStringArray(R.array.role);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Role_Names);
        roleAutoCompleteTextView.setAdapter(roleAdapter);


        return root;

    }//end of on create view

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//email password sign up
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fulname = fullname.getText().toString().trim();
                String usermail = email.getText().toString().trim();
                String county = county_of_res.getText().toString().trim();
                String campus = campus_of_study.getText().toString().trim();
                String role = who_im_i.getText().toString().trim();
                String phoneNum = phoneNumber.getText().toString().trim();
                String passwrd = password1.getText().toString().trim();
                String passwrd1 = password2.getText().toString().trim();


                //check if the email and password fields are empty, if empty, show error

                if (TextUtils.isEmpty(usermail)){
                    email.setError("Email cannot be empty!");
                    email.requestFocus();
                }

                else if (TextUtils.isEmpty(fulname)){
                    fullname.setError("PLease enter your name");
                    fullname.requestFocus();
                }

                else if (TextUtils.isEmpty(phoneNum)){
                    phoneNumber.setError("Phone number is required!");
                    phoneNumber.requestFocus();
                }
                else if (TextUtils.isEmpty(campus)){
                    campus_of_study.setError("Please select a campus");
                    campus_of_study.requestFocus();
                }
                else if (TextUtils.isEmpty(county)){
                    county_of_res.setError("County of residence cannot be empty!");
                    county_of_res.requestFocus();
                }
                else if (TextUtils.isEmpty(role)){
                    who_im_i.setError("Please tell us who you are");
                    who_im_i.requestFocus();
                }
                else if (TextUtils.isEmpty(passwrd)){
                    password1.setError("Password cannot be empty!");
                    password2.requestFocus();
                }
                //if passwords don't match
                else if(!passwrd.equals(passwrd1)){
                    password2.setError("Password did not match, please try again!");
                    password2.setText("");
                    password2.requestFocus();
                }
                else if(passwrd.length()<6){
                    password1.setError("Minimum password length is 6");
                    password1.setText("");
                    password1.requestFocus();
                    password2.setError("Minimum password length is 6");
                    password2.setText("");
                }

                else {
                    if (phoneNum.length() < 10) {
                        phoneNumber.setError("Phone number required length is 10");
                        phoneNumber.requestFocus();
                    } else{
                        progressDialog.setTitle("Registering new user");
                    progressDialog.setMessage("Please wait as we register you...");
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(usermail, passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //check if the creation task was successful
                            if (task.isSuccessful()) {

                                //register to database
                                //create user object
                                User user = new User(fulname, usermail, phoneNum, county, campus, role);
                                FirebaseDatabase.getInstance().getReference("Users").child("Customers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Registered Successfully " + usermail, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getActivity(), MainActivity.class); //open get started
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getActivity(), "Database Registration failure, please try again or contact app support if problem persists", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                            } else {
                                //dismiss loading bar
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Registration Error, please try again " + task.getException().getMessage(), Toast.LENGTH_SHORT).show(); //show toast on error and also the error
                            }
                        }
                    });
                }
                }
                
            }
        });//end of email password sign up

//google sign up
        fabGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"You clicked google",Toast.LENGTH_SHORT).show();

            }
        }); //end of google sign up
    }//end of oncreated view
}