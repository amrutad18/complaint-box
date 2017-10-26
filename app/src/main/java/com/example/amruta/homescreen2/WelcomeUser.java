package com.example.amruta.homescreen2;

/**
 * Created by amruta on 25/10/17.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
public class WelcomeUser extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user);
        getSupportActionBar().setTitle("");
        initViews();
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        System.out.println(emailFromIntent+"ghgh");
        textViewName.setText(emailFromIntent);

    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (TextView)findViewById(R.id.textView);
    }
}
