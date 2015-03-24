package com.vuadvocate.advocode2015;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;

public class StartActivity extends Activity {

   // private EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    //    e1=(EditText)findViewById(R.id.location);

    }

    public void onButtonClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        //String zipcode = e1.getText().toString();
        //intent.putExtra(value,zipcode);
        startActivity(intent);
    }

}
