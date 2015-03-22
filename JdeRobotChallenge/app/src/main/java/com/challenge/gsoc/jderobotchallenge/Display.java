package com.challenge.gsoc.jderobotchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by MAC on 23/03/15.
 */
public class Display extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_item);
        Intent in =getIntent();

        TextView tv =(TextView)findViewById(R.id.amount);
        TextView tv2 =(TextView)findViewById(R.id.currency);

        tv.setText("Amount: "+in.getStringExtra("Amount"));
        tv2.setText("Currency: "+in.getStringExtra("Currency"));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
