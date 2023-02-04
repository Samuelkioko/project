package com.example.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {

    //variables
    FloatingActionButton floatingActionButton;
    float v=0;
    TextView fillme, fresh, everything, small;
    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentA newInstance(String param1, String param2) {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_a, container, false);

        floatingActionButton = root.findViewById(R.id.fab_On);
        fillme = root.findViewById(R.id.txtTop);
        fresh = root.findViewById(R.id.txtFresh);
        everything = root.findViewById(R.id.txtEverything);
        imageView = root.findViewById(R.id.imgFrag1);
        small = root.findViewById(R.id.txtSmall);

        //animate fab
        floatingActionButton.setTranslationY(300);
        floatingActionButton.setAlpha(v);
        floatingActionButton.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(2000).start();


        //animate fillme
        fillme.setTranslationY(300);
        fillme.setAlpha(v);
        fillme.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        //animate fresh
        fresh.setTranslationY(300);
        fresh.setAlpha(v);
        fresh.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        //animate everything
        everything.setTranslationY(300);
        everything.setAlpha(v);
        everything.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        //animate small
        small.setTranslationY(300);
        small.setAlpha(v);
        small.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        //animate imageView
        imageView.setTranslationY(300);
        imageView.setAlpha(v);
        imageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();




        //fab
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"swipe from left to right to start",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}