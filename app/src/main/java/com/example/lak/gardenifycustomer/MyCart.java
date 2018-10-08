package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    ListView cartitems;
    Button placeorder;

    DatabaseReference mDatabase;

    String name,city;

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    User user;

    Provider tempprovider;


    ArrayList<String> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        cartitems=findViewById(R.id.cartlistView);
        placeorder=findViewById(R.id.cartbutton);

        sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        name=sharedPreferences.getString(Key,"");
        city=sharedPreferences.getString(Key2,"");

        mDatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(name);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                cart=user.getCart();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, cart);
                cartitems.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<cart.size();i++)
                {
                    final String order=cart.get(i);
                    String temp[]=order.split("@",-1);
                    String name=temp[0];
                    final DatabaseReference mDatabase2=FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);
                    mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Provider provider=dataSnapshot.getValue(Provider.class);
                            Log.d("lakapp","provider="+provider.getName());
                            provider.addpendingorder(order);
                            moveToPendingOrder(mDatabase2,provider);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });



    }
    public void moveToPendingOrder(DatabaseReference mDatabase,Provider provider){
        mDatabase.setValue(provider);
    }
}
