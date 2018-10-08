package com.example.lak.gardenifycustomer;

import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddToCart extends AppCompatActivity {

    TextView details;
    Button AddButton,CancelButton,timebutton;

    String name,city,order,stype,sname,time,providerDetails,price,cname;

    DatabaseReference mDatabase,MDatabase2;

    User provider,customer;

    boolean flag=false;

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        Bundle extras = getIntent().getExtras();
        name=extras.getString("Name");
        city=extras.getString("City");
        order=extras.getString("Order");
        sname=extras.getString("Sname");
        stype=extras.getString("Stype");
        price=extras.getString("Price");

        sharedPreferences=getSharedPreferences(MyPrefs,Context.MODE_PRIVATE);
        cname=sharedPreferences.getString(Key,"");

        details=findViewById(R.id.orderdetails);
        AddButton=findViewById(R.id.cartadd);
        CancelButton=findViewById(R.id.cartcancel);
        timebutton=findViewById(R.id.timepicker);

        String temp[]=order.split("@",-1);
        Log.d("Daily",temp[0]);
        final String providername=temp[0];

        details.setText("");

       mDatabase= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(providername);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                providerDetails="Name of Gardener:"+td.get("name").toString()+"\nAddress of Gardener:"+td.get("address1").toString()+"\nContact No:"+td.get("mobileno").toString();
                details.setText(providerDetails);

                Log.d("Daily",""+td.keySet());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       timebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar mcurrentTime = Calendar.getInstance();
               int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
               int minute = mcurrentTime.get(Calendar.MINUTE);
               TimePickerDialog mTimePicker;
               mTimePicker = new TimePickerDialog(AddToCart.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                       String AM;
                       if(selectedHour>12){
                           selectedHour-=12;
                           AM="PM";
                       }
                       else
                       {
                           AM="AM";
                       }
                       time=selectedHour + ":" +selectedMinute+":"+AM;
                       Toast.makeText(getApplicationContext(),time,Toast.LENGTH_SHORT).show();
                       flag=true;
                   }
               }, hour, minute, false);//Yes 24 hour time
               mTimePicker.setTitle("Select Time");
               mTimePicker.show();
           }
       });

       AddButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mDatabase=FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(name);
               mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(flag==true) {
                           order=providername+"@"+cname+"@"+time+"@"+price;
                           customer = dataSnapshot.getValue(User.class);
                           new AlertDialog.Builder(AddToCart.this)
                                   .setTitle("Add To  Cart")
                                   .setMessage("Do you really want to Add?")
                                   .setIcon(android.R.drawable.ic_delete)
                                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                       public void onClick(DialogInterface dialog, int whichButton) {
                                           customer.addToCart(order);
                                           mDatabase.setValue(customer);
                                           Toast.makeText(getApplicationContext(), "Addded To Cart Succesfully", Toast.LENGTH_SHORT).show();
                                           finish();
                                       }})
                                   .setNegativeButton(android.R.string.no, null).show();
                       }
                       else
                           Toast.makeText(getApplicationContext(),"Select Time",Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       });


    }
}
