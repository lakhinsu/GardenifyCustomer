package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {
    TextView NameBox;
    EditText Phonebox,addbox,emailbox;

    Button update;

    DatabaseReference databaseReference;

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        NameBox=findViewById(R.id.settingsname);
        Phonebox=findViewById(R.id.settingsphone);
        addbox=findViewById(R.id.settingsaddress);
        update=findViewById(R.id.update);
        emailbox=findViewById(R.id.settingsemail);

        String name,city;
        sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        name=sharedPreferences.getString(Key,"");
        city=sharedPreferences.getString(Key2,"");

        NameBox.setText(name);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(name);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                Phonebox.setText(user.getPhoneno());
                addbox.setText(user.getAddress());
                emailbox.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add=addbox.getText().toString();
                String phone=Phonebox.getText().toString();
                String email=emailbox.getText().toString();
                if(add.length()!=0 && phone.length()!=0 && email.length()!=0){
                    user.setAddress(add);
                    user.setPhoneno(phone);
                    user.setEmail(email);
                    databaseReference.setValue(user);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
