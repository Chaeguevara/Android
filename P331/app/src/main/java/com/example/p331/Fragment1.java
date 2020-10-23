package com.example.p331;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment1 extends Fragment {
    Button button;
    MainActivity m ;

    public Fragment1() {
        // Required empty public constructor
    }
    public Fragment1(MainActivity m){
        this.m = m;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup)inflater.inflate(
                R.layout.fragment_1,container,false);
        button = viewGroup.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(m, "aaa", Toast.LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }

}