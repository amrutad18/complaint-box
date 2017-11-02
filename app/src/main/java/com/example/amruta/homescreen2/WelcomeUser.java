package com.example.amruta.homescreen2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
public class WelcomeUser extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = WelcomeUser.this;

    private TextView textViewName1,textViewName2;
    String emailFromIntent, resolved_count = "3", pending_count = "6";
    private AppCompatButton appCompatButtonRegister;
    private AppCompatButton appCompatButtonViewComplaints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user);
        getSupportActionBar().setTitle("Welcome  "+getIntent().getStringExtra("EMAIL").toString().trim());
        initViews();
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName1.setText(resolved_count);
        textViewName2.setText(pending_count);
        initListeners();
    }


    //This method is to initialize views
    private void initViews() {
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatButtonViewComplaints = (AppCompatButton) findViewById(R.id.appCompatButtonViewComplaints);
        textViewName1 = (TextView)findViewById(R.id.resolved_ring);
        textViewName2 = (TextView)findViewById(R.id.pending_ring);
    }


    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonViewComplaints.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                Intent accountsIntent = new Intent(activity,RegisterComplaint.class);
                //This sends the email of the user to the new activity

                accountsIntent.putExtra("EMAIL",emailFromIntent);
                startActivity(accountsIntent);
                break;

            case R.id.appCompatButtonViewComplaints:
                Intent accountsIntent2 = new Intent(activity,UsersListActivity.class);
                //This sends the email of the user to the new activity

                accountsIntent2.putExtra("EMAIL",emailFromIntent);
                startActivity(accountsIntent2);
                break;
        }
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

                return true;

            case R.id.logout:
                Intent logOutIntent = new Intent(activity,MainActivity.class);
                startActivity(logOutIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
