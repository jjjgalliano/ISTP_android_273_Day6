package com.example.user.myandroidapp;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentTestActivity extends AppCompatActivity implements View.OnClickListener
{


    TestFragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        ((Button)findViewById(R.id.fragment1Button)).setOnClickListener(this) ;
        ((Button)findViewById(R.id.fragment2Button)).setOnClickListener(this) ;

        fragments = new TestFragment[2];
        fragments[0] = TestFragment.newInstance("this is fragemtn 1");
        fragments[0].fragmentName ="ff1";
        fragments[1] = TestFragment.newInstance("this is fragemtn 2");
        fragments[1].fragmentName ="ff2";
    }



    // framelayout
    // gragment will put on lefttop   layer by layer

    void displayFragment(Fragment fragment ,boolean canBereversed){
        FragmentManager fragmentManager= getSupportFragmentManager(); // getFragmentManager();
        FragmentTransaction  transaction= fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        if(canBereversed)
        {
            transaction.addToBackStack(null); //null -> put to default stack
        }else
        {

        }

        transaction.commit();

    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.fragment1Button)
        {
            displayFragment(fragments[0],true);
        }
        else if (viewId == R.id.fragment2Button)
        {
            displayFragment(fragments[1],true);
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() >0)
        {
            fragmentManager.popBackStack(); // 回覆
        }else
        {
            super.onBackPressed(); // back to last activity
        }


    }
}
