package com.example.android.speakingnewsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.speakingnewsapp.R;

public class FeaturesScreen extends AppCompatActivity implements View.OnClickListener {
    CardView eMenuCard;
   // CardView entertainmentCard;
    CardView services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eMenuCard = (CardView) findViewById(R.id.eMenuCard);
     //   entertainmentCard = (CardView) findViewById(R.id.entertainmentCard);
        services = (CardView) findViewById(R.id.services);
        eMenuCard.setOnClickListener(this);
        //entertainmentCard.setOnClickListener(this);
        services.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()== R.id.action_safety) {
            Intent i= new Intent(FeaturesScreen.this, SafetyActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.eMenuCard)
        {
            startActivity(new Intent(this,MainActivity.class));
        }
        if(v.getId() == R.id.services)
        {
            startActivity(new Intent(FeaturesScreen.this,ContactScreen.class));
        }
    }
}
