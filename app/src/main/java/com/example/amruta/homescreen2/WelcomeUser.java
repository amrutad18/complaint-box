package com.example.amruta.homescreen2;

/**
 * Created by amruta on 25/10/17.
 */


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

    private TextView textViewName,textViewName1,textViewName2;
    String emailFromIntent, resolved_count = "3", pending_count = "6";
    private AppCompatButton appCompatButtonRegister;
    private AppCompatButton appCompatButtonViewComplaints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user);
        getSupportActionBar().setTitle("");
        initViews();
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        System.out.println(emailFromIntent+"ghgh");
        textViewName.setText(emailFromIntent);
        textViewName1.setText(resolved_count);
        textViewName2.setText(pending_count);
        initListeners();
        //initObjects();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (TextView)findViewById(R.id.textView);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatButtonViewComplaints = (AppCompatButton) findViewById(R.id.appCompatButtonViewComplaints);
        textViewName1 = (TextView)findViewById(R.id.resolved_ring);
        textViewName2 = (TextView)findViewById(R.id.pending_ring);
    }


    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonViewComplaints.setOnClickListener(this);
        //appCompatTextViewLoginLink.setOnClickListener(this);

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
                Intent homeIntent = new Intent(activity, WelcomeUser.class);
                homeIntent.putExtra("EMAIL",emailFromIntent);
                startActivity(homeIntent);
                // User chose the "My Account" item, show the app settings UI...
                return true;

            case R.id.edit_profile:

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
