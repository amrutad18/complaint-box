package com.example.amruta.homescreen2;

/**
 * Created by amruta on 25/10/17.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
public class WelcomeUser extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = WelcomeUser.this;
    private TextView textViewName;
    String emailFromIntent;
    private AppCompatButton appCompatButtonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_register);
        getSupportActionBar().setTitle("");
        initViews();
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        System.out.println(emailFromIntent+"ghgh");
        textViewName.setText(emailFromIntent);
        initListeners();
        //initObjects();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (TextView)findViewById(R.id.textView);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
    }


    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
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
        }
    }
}
