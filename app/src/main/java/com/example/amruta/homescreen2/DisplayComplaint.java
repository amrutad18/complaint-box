package com.example.amruta.homescreen2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by amruta on 27/10/17.
 */

public class DisplayComplaint extends AppCompatActivity {
    private TextView textViewName;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_complaint);
        getSupportActionBar().setTitle("");
        initViews();
        //initViews();
        //emailFromIntent = getIntent().getStringExtra("EMAIL");
        System.out.println("ghgh");
        textViewName.setText("SEE YOUR COMPLAINTS HERE");
        //initListeners();
        //initObjects();
    }

    private void initViews() {
        textViewName = (TextView)findViewById(R.id.textView);
        //appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // User chose the "My Account" item, show the app settings UI...
                return true;

            case R.id.logout:

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
