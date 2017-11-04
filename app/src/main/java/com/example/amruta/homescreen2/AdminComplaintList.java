package com.example.amruta.homescreen2;

/**
 * Created by amruta on 27/10/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.amruta.homescreen2.R;
import com.example.amruta.homescreen2.adapters.AdminRecyclersAdapter;
import com.example.amruta.homescreen2.Model.Complaint;
import com.example.amruta.homescreen2.sql.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class AdminComplaintList extends AppCompatActivity {

    private AppCompatActivity activity = AdminComplaintList.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<Complaint> listUsers;
    private AdminRecyclersAdapter adminRecyclerAdapter;
    private DataBaseHelper databaseHelper;
    String emailFromIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_complaint);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        System.out.println("Inside initViews");
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);

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
}
