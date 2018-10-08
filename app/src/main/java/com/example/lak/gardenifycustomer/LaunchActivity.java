package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    Button login,reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        login=(Button) findViewById(R.id.launchlogin);
        reg=(Button) findViewById(R.id.launchreg);

        sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(Key)){
            String name=sharedPreferences.getString(Key,"");
            if(name.length()>=0){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
