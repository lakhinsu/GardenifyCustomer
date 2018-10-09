package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeclinedOrders extends AppCompatActivity {

    ListView declinedorderslist;
    DatabaseReference mDatabase;

    String name,city;

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    ArrayList<String> declinedorder;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declined_orders);

        setTitle("Declined Orders");

        declinedorderslist=findViewById(R.id.declinedorders);

        sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        name=sharedPreferences.getString(Key,"");
        city=sharedPreferences.getString(Key2,"");

        mDatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(name);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                declinedorder=user.getDeclineorders();
                ArrayList<String> temp=new ArrayList<>();
                for(int i=0;i<declinedorder.size();i++){
                    String temp2[]=declinedorder.get(i).split("@",-1);
                    if(temp2.length==4){
                        String pname=temp2[0];
                        String otime=temp2[2];
                        String oprice=temp2[3];
                        String disp="Provider: "+pname+" Pay:"+oprice;
                        temp.add(disp);
                    }
                    else if(temp2.length==5){
                        String pname=temp2[0];
                        String otime=temp2[2];
                        String odate=temp2[3];
                        String oprice=temp2[4];
                        String disp="Provider: "+pname+" Pay:"+oprice;
                        temp.add(disp);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, temp);
                declinedorderslist.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
