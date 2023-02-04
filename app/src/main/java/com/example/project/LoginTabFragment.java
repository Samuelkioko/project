package com.example.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class LoginTabFragment extends Fragment {
    EditText emailLogin;
    TextView reset;
    private Button btnLogin;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;
    FloatingActionButton fabFbSignIn,fabGoogleSignIn,fabPhoneSignIn;
    float v=0;


    private FirebaseAuth mAuth;
    //progress dialogs
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    //For change on state (start and stop)
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    GoogleSignInClient mGoogleSignInClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.login_tab_fragment, container,false);

        emailLogin=root.findViewById(R.id.edtEmailLogin);
        reset=root.findViewById(R.id.txtForgot);
        btnLogin=root.findViewById(R.id.btnLogin);
        textInputLayout=root.findViewById(R.id.lytPasswordLogin);
        textInputEditText=root.findViewById(R.id.edtPasswordLogin);
        fabFbSignIn=root.findViewById(R.id.fab_facebook);
        fabGoogleSignIn=root.findViewById(R.id.fab_google);
        fabPhoneSignIn=root.findViewById(R.id.fab_phone);


        emailLogin.setTranslationY(300);

        reset.setTranslationY(300);
        btnLogin.setTranslationY(300);
        textInputLayout.setTranslationY(300);
        textInputEditText.setTranslationY(300);
        fabFbSignIn.setTranslationY(300);
        fabGoogleSignIn.setTranslationY(300);
        fabPhoneSignIn.setTranslationY(300);

        emailLogin.setAlpha(v);

        reset.setAlpha(v);
        btnLogin.setAlpha(v);
        textInputLayout.setAlpha(v);
        textInputEditText.setAlpha(v);
        fabFbSignIn.setAlpha(v);
        fabGoogleSignIn.setAlpha(v);
        fabPhoneSignIn.setAlpha(v);


        emailLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        reset.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        textInputLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        textInputEditText.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        fabFbSignIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        fabGoogleSignIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        fabPhoneSignIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();


        return root;

    }//end on create view

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth= FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(getActivity()); //for login button
        progressDialog2=new ProgressDialog(getActivity());//for reset password YES click

        //EMAIL PASSWORD LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login code
                String usermaillog = emailLogin.getText().toString();
                String passwrdlog = textInputEditText.getText().toString();

                //check if the email and password fields are empty, if empty, show error
                if (TextUtils.isEmpty(usermaillog)){
                    emailLogin.setError("Email cannot be empty!");
                    emailLogin.requestFocus();
                }
                else if (TextUtils.isEmpty(passwrdlog)){
                    textInputEditText.setError("Password cannot be empty!");
                    textInputEditText.requestFocus();
                }
                else{
                    progressDialog.setTitle("Verification");
                    progressDialog.setMessage("A moment please as we log you in...");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(usermaillog,passwrdlog).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //dismiss progress dialog
                                progressDialog.dismiss();

                                //check if email is verified
                                Toast.makeText(getActivity(), "Log in successful, welcome " + emailLogin.getText(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), WelcomeActivity.class)); //open next activity if login was successful
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"Log in Error please try again "+task.getException().getMessage(),Toast.LENGTH_LONG).show(); //show toast on error and also the error
                            }
                        }
                    });
                }
            }
        });//END OF EMAIL PASSWORD LOGIN

        //GOOGLE SIGN IN
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        fabGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        //end of google sign in method


        //firebase auth listener check if user was logged in if yes send him/her to the next activity
        firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null)//if user is logged in
                {
                    Intent intent=new Intent(getActivity(), WelcomeActivity.class);//open next activity
                    startActivity(intent);
                }
            }
        };//end of state changed

        //reset password
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an edit text field to let user enter their email to get reset link
                final EditText resetEmail = new EditText(v.getContext());
                final AlertDialog.Builder resetDialog = new AlertDialog.Builder(v.getContext());
                //give title and message to the alert d ialog
                resetDialog.setTitle("Reset password");
                resetDialog.setMessage("Email");
                //set the edit text to the view of the alert dialog
                resetDialog.setView(resetEmail);


                //handle the buttons on reset dialog (Yes and No)
                resetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and check if its valid/exists in database then send link else display an error
                        //OK button

                            progressDialog2.setTitle("Password reset");
                            progressDialog2.setMessage("Sending reset link, please wait....");
                            progressDialog2.show();

                            //extract the entered email
                            String mail = resetEmail.getText().toString();
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() { //if sending was successful
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog2.dismiss();
                                    Toast.makeText(getActivity(), "Link sent to " + resetEmail.getText() + ". Please click on the link to proceed", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {       //if sending failed
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog2.dismiss();

                                    Toast.makeText(getActivity(), "Link not sent! " + resetEmail.getText() + ". Error(s) incurred " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                });
                //Cancel button
                resetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the alert dialog
                        Toast.makeText(getActivity(),"Reset cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                resetDialog.create().show(); //create it and show
            }
        });


    }//END OF ONCREATED VIEW

    //GOOGLE SIGN IN METHOD
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }//END OF GOOGLE SIGN IN METHOD
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }//end of on activity result




//firebaseAuthWithGoogle
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(),"Sign in successful welcome "+user.getEmail(),Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }//end of firebase auth with google

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getActivity(),WelcomeActivity.class);
        startActivity(intent);
    }


    //firebase auth listener start and stop
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
}
