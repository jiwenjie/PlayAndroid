package com.example.root.playandroidtest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.playandroidtest.R;

/**
 * Created by Root on 2018/3/13.
 */

public class HomeFragment extends Fragment {

    private TextView index_home_textView;

    public HomeFragment() {  }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        contentView(rootView);

        return contentView(rootView);
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private View contentView(View rootView){
        index_home_textView = (TextView) rootView.findViewById(R.id.index_home_text);
        index_home_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), TestLink.class);
//                startActivity(intent);
            }
        });

        return rootView;
    }
}
