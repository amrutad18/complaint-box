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
import android.view.View;

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
        //private com.example.amruta.homescreen2.sql.databaseHelper databaseHelper;
        private Complaint complaint;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.complaint_register);
            getSupportActionBar().hide();

            initViews();
            initListeners();
            initObjects();
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
                    postDataToSQLite();
                    break;
            }
        }

        /**
         * This method is to validate the input text fields and post data to SQLite
         */
        private void postDataToSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextModel, textInputLayoutModel, getString(R.string.error_message_name))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextDescription, textInputLayoutDescription, getString(R.string.error_message_email))) {
                return;
            }



                complaint.setModelNo(textInputEditTextModel.getText().toString().trim());
                complaint.setDetails(textInputEditTextDescription.getText().toString().trim());
                complaint.setUser(getIntent().getStringExtra("EMAIL").toString().trim());
                int d=(int) (new Date().getTime()/1000);
                complaint.setFileDate(d);
                databaseHelper.addComplaint(complaint);
                if(databaseHelper.checkComplaint(getIntent().getStringExtra("EMAIL"))){
                    System.out.println("Database exists");
                }
                else
                {
                    System.out.println("No ddd");
                }
                // Snack Bar to show success message that record saved successfully
                Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                //emptyInputEditText();
                Intent user = new Intent(activity,UsersListActivity.class);
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
    }

