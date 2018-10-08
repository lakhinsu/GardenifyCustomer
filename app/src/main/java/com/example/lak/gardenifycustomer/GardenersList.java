package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class GardenersList extends AppCompatActivity {

    DatabaseReference mDatabase;
    ListView gardenersview;


    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    String Sname,Stype,city,name,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardeners_list);

        gardenersview=findViewById(R.id.gardenerslist);

        Bundle extras = getIntent().getExtras();
        Sname = extras.getString("ServiceName").toString();
        Stype=extras.getString("ServiceType").toString();

        sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);


        name=sharedPreferences.getString(Key,"");
        city=sharedPreferences.getString(Key2,"");

        mDatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("SERVICES").child(Stype).child(Sname);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                    Log.d("Daily",""+td);
                    final ArrayList<String> gardeners= new ArrayList<>();
                    final ArrayList<String> tempGardeners=new ArrayList<>();
                    //services=(ArrayList<String>) td.keySet();
                    for (String key : td.keySet()) {
                        String serv=td.get(key).toString();
                        tempGardeners.add(serv);
                        String temp[]=serv.split("@",-1);
                        Log.d("GARDLIST",""+temp+" org="+serv);
                        gardeners.add("Provider:"+temp[0]+" Price:"+temp[1]+" Rs");
                        price=temp[1];
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, gardeners);
                    gardenersview.setAdapter(arrayAdapter);
                    gardenersview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                             public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                                 final String text = tempGardeners.get(position);
                                                                 //Toast.makeText(getApplicationContext(), "" + text, Toast.LENGTH_SHORT).show();

                                                                 if(Stype.equals("DAILY")) {
                                                                     Intent intent = new Intent(getApplicationContext(), AddToCart.class);
                                                                     intent.putExtra("Name", name);
                                                                     intent.putExtra("City", city);
                                                                     intent.putExtra("Stype", Stype);
                                                                     intent.putExtra("Sname", Sname);
                                                                     intent.putExtra("Order", text);
                                                                     intent.putExtra("Price",price);
                                                                     startActivity(intent);
                                                                 }
                                                                 else
                                                                 {
                                                                     Intent intent = new Intent(getApplicationContext(), AddToCartMonthly.class);
                                                                     intent.putExtra("Name", name);
                                                                     intent.putExtra("City", city);
                                                                     intent.putExtra("Stype", Stype);
                                                                     intent.putExtra("Sname", Sname);
                                                                     intent.putExtra("Order", text);
                                                                     intent.putExtra("Price",price);
                                                                     startActivity(intent);
                                                                 }
                                                             }
                                                         }
                    );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
