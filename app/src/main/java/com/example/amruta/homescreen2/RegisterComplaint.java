package com.example.amruta.homescreen2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.amruta.homescreen2.Model.Complaint;
import com.example.amruta.homescreen2.Model.User;
import com.example.amruta.homescreen2.helper.InputValidation;
import com.example.amruta.homescreen2.sql.DataBaseHelper;

import java.util.Date;

/**
 * Created by amruta on 27/10/17.
 */

public class RegisterComplaint extends AppCompatActivity implements View.OnClickListener{


        private final AppCompatActivity activity = com.example.amruta.homescreen2.RegisterComplaint.this;

        private NestedScrollView nestedScrollView;

        private TextInputLayout textInputLayoutModel;
        private TextInputLayout textInputLayoutDescription;

        private TextInputEditText textInputEditTextModel;
        private TextInputEditText textInputEditTextDescription;

        private AppCompatButton appCompatButtonRegister;


        private InputValidation inputValidation;
        private DataBaseHelper databaseHelper;

        private Complaint complaint;
        private String product="Phone", emailFromIntent;
        private int priority = 1;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.complaint_register);
            getSupportActionBar().setTitle("New Complaint");
            emailFromIntent = getIntent().getStringExtra("EMAIL");
            initViews();
            initListeners();
            initObjects();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onSupportNavigateUp(){
            Intent intent = new Intent(this, WelcomeUser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EMAIL",emailFromIntent);
            startActivity(intent);
            return true;
        }

        public void onRadioButtonClicked(View view){
            boolean checked = ((RadioButton)view).isChecked();
            switch(view.getId()){
                case R.id.phone:
                    if(checked){
                        product = "Phone";
                    }
                    break;
                case R.id.laptop:
                    if(checked){
                        product = "Laptop";
                    }
                    break;
                case R.id.ac:
                    if(checked){
                        product = "AC";
                    }
                    break;
            }


        }

        public void onSwitchButtonClicked(View view){
            boolean checked = ((Switch)view).isChecked();
            if(checked)
            {
                priority = 0;
            }
            else
            {
                priority = 1;
            }
        }

        /**
         * This method is to initialize views
         */
        private void initViews() {
            nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

            textInputLayoutModel = (TextInputLayout) findViewById(R.id.textInputLayoutModel);
            textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutDescription);

            textInputEditTextModel = (TextInputEditText) findViewById(R.id.textInputEditTextModel);
            textInputEditTextDescription = (TextInputEditText) findViewById(R.id.textInputEditTextDescription);

            appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);


        }

        /**
         * This method is to initialize listeners
         */
        private void initListeners() {
            appCompatButtonRegister.setOnClickListener(this);

        }

        /**
         * This method is to initialize objects to be used
         */
        private void initObjects() {
            inputValidation = new InputValidation(activity);
            databaseHelper = new DataBaseHelper(activity);
            complaint = new Complaint();

        }


        /**
         * This implemented method is to listen the click on view
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.appCompatButtonRegister:
                    System.out.println("posting data...");
                    postDataToSQLite();
                    break;
            }
        }

        /**
         * This method is to validate the input text fields and post data to SQLite
         */
        private void postDataToSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextModel, textInputLayoutModel, "Enter Model no")) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextDescription, textInputLayoutDescription, "Enter details")) {
                return;
            }



                complaint.setModelNo(textInputEditTextModel.getText().toString().trim());
                complaint.setDetails(textInputEditTextDescription.getText().toString().trim());
                complaint.setUser(getIntent().getStringExtra("EMAIL").toString().trim());
                complaint.setProductType(product);
                System.out.println(priority);
                complaint.setPriority(priority);
                databaseHelper.addComplaint(complaint);
                if(databaseHelper.checkComplaint(getIntent().getStringExtra("EMAIL"))){
                    System.out.println("Database exists");
                }
                else
                {
                    System.out.println("No database");
                }
                // Snack Bar to show success message that record saved successfully
                Toast.makeText(getApplicationContext(), getString(R.string.success_message), Toast.LENGTH_SHORT).show();
                //emptyInputEditText();
                Intent user = new Intent(activity,WelcomeUser.class);
                //This sends the email of the user to the new activity

                user.putExtra("EMAIL", getIntent().getStringExtra("EMAIL").toString().trim());
                emptyInputEditText();
                startActivity(user);




        }

        /**
         * This method is to empty all input edit text
         */
        private void emptyInputEditText() {
            textInputEditTextModel.setText(null);
            textInputEditTextDescription.setText(null);
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

