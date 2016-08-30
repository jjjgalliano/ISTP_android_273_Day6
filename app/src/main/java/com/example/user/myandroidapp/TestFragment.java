package com.example.user.myandroidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myandroidapp.CustomizedFragment;

/**
 * Created by Gillian on 16/8/30.
 */
public class TestFragment extends CustomizedFragment {

    final static String  messageKey = "message";
    public String mMessage ;
    View fragmentview;

    public static TestFragment  newInstance(String message)
    {
        Bundle args = new Bundle();
        args.putString(messageKey, message);

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);

        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         Bundle args = getArguments();
        mMessage = args.getString(messageKey);

    }



    // @NULLABLE  FUNCTION CAN RETURN NULL
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // similar to adapter getview
        // view change will call this , so -->fragmentview

        super.onCreateView(inflater, container, savedInstanceState);
        if(fragmentview == null)
        {
            fragmentview = inflater.inflate(R.layout.fragment_test,null);
            TextView textView = (TextView) fragmentview.findViewById(R.id.testView);
            textView.setText(mMessage);
        }

        return  fragmentview;

    }
}
