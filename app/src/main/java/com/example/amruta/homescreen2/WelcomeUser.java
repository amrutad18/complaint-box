package com.example.amruta.homescreen2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amruta.homescreen2.Model.User;
import com.example.amruta.homescreen2.sql.DataBaseHelper;

public class WelcomeUser extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = WelcomeUser.this;

    private TextView textViewName1,textViewName2;
    String emailFromIntent;
    long resolved_count, pending_count;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatButton appCompatButtonViewComplaints;
    private DataBaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user);
        getSupportActionBar().setTitle("Welcome  "+getIntent().getStringExtra("EMAIL").toString().trim());
        initViews();
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        System.out.println(emailFromIntent+"ghgh");
        user = new User();
        databaseHelper = new DataBaseHelper(activity);
        user.setEmail(emailFromIntent);
        System.out.println(user.getEmail()+"ghgh");
        resolved_count = databaseHelper.getResolvedNum(user);
        pending_count = databaseHelper.getPendingNum(user);
        textViewName1.setText(String.valueOf(resolved_count));
        textViewName2.setText(String.valueOf(pending_count));

        initListeners();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
        //finish();
        //return true;
    }*/

    /**
     * This method is to initialize views
     */
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
                Intent loginscreen=new Intent(this,MainActivity.class);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginscreen);
                this.finish();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog_logout = new AlertDialog.Builder(this);
        dialog_logout.setMessage(R.string.dialog_logout)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginscreen=new Intent(activity,MainActivity.class);
                        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginscreen);
                        activity.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        dialog_logout.show();
    }
}
