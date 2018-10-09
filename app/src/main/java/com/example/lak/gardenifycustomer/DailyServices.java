package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DailyServices extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView servicesview;


    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_services);
        setTitle("Daily Services");

        servicesview = findViewById(R.id.dailyservicesview);

        sharedPreferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        String city=sharedPreferences.getString(Key2,"");

        Bundle extras = getIntent().getExtras();
        final String Stype=extras.getString("Stype").toString();

        mDatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("SERVICES").child(Stype);



        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    final ArrayList<String> services=new ArrayList<>();
                    //services=(ArrayList<String>) td.keySet();
                    for(String key:td.keySet()){
                        services.add(key);
                    }

                    Log.d("Daily",""+services);

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,services);
                    servicesview.setAdapter(arrayAdapter);
                    servicesview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                                String text = services.get(position);
                                                                Toast.makeText(getApplicationContext(),""+text,Toast.LENGTH_SHORT).show();
                                                                Intent I=new Intent(getApplicationContext(),GardenersList.class);
                                                                I.putExtra("ServiceName",text);
                                                                I.putExtra("ServiceType",Stype);
                                                                startActivity(I);

                                                            }
                                                        }
                    );

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                // ...
            }
        });
    }
}
