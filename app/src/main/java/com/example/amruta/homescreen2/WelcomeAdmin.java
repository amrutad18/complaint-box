package com.example.amruta.homescreen2;

/**
 * Created by amruta on 27/10/17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.amruta.homescreen2.R;
import com.example.amruta.homescreen2.adapters.AdminRecyclersAdapter;
import com.example.amruta.homescreen2.Model.Complaint;
import com.example.amruta.homescreen2.sql.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class WelcomeAdmin extends AppCompatActivity {

    private AppCompatActivity activity = WelcomeAdmin.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<Complaint> listUsers;
    private AdminRecyclersAdapter adminRecyclerAdapter;
    private DataBaseHelper databaseHelper;
    String emailFromIntent;
    private Button appCompatButtonInProcess;
    private Button appCompatButtonResolved;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
        initListeners();

    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        System.out.println("Inside initViews");
        appCompatButtonResolved = (Button) findViewById(R.id.resolved);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    private void initListeners() {
        if(appCompatButtonResolved==null)
        {
            System.out.println("HEHEHe");
        }

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        adminRecyclerAdapter = new AdminRecyclersAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(adminRecyclerAdapter);
        databaseHelper = new DataBaseHelper(activity);

        emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                List<Complaint>temp;
                listUsers.addAll(databaseHelper.getAllComplaints());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adminRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
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
