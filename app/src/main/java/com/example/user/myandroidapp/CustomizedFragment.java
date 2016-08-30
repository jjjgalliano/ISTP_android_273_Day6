package com.example.user.myandroidapp;

//import android.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gillian on 16/8/30.
 */
public class CustomizedFragment extends Fragment { // user supprt vergin  in lower level app

    public final static String debug_tag  = "fragmentTest";
    public String  fragmentName = ""; // now this is which fragment

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(debug_tag,fragmentName+": onAttach(activity)");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(debug_tag,fragmentName+": onAttach(CONTEXT)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {   //
        super.onCreate(savedInstanceState);
        Log.d(debug_tag,fragmentName+": onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(debug_tag,fragmentName+": onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(debug_tag,fragmentName+": onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(debug_tag,fragmentName+": onResume");
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d(debug_tag,fragmentName+": onStop");
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d(debug_tag,fragmentName+": onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(debug_tag,fragmentName+": onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(debug_tag,fragmentName+": onDestroyView");
        super.onDestroyView();
    }
}
