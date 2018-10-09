package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText namebox,passbox,citybox;
    Button login;

    DatabaseReference databaseReference;

    final String MyPrefs="UserPrefs";
    final String Key="Name";
    final String Key2="City";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        namebox=(EditText) findViewById(R.id.loginname);
        passbox=(EditText)findViewById(R.id.loginpass);
        citybox=(EditText)findViewById(R.id.logincity);

        login=(Button) findViewById(R.id.loginbutton);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = namebox.getText().toString();
                final String password = passbox.getText().toString();
                final String city = citybox.getText().toString();
                if (name.length()==0 && password.length() == 0 && city.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Incorrect Details", Toast.LENGTH_SHORT).show();
                }
                else{
                databaseReference = FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("CUSTOMER").child(name);
                User user = new User();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        Log.d("Value", "Name is: " + user.getName() + "Pass is:" + user.getPassword());
                        if (!password.equals("" + user.getPassword()) && name.length() == 0 && password.length() == 0 && city.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Incorrect Details", Toast.LENGTH_SHORT).show();
                        } else {
                            sharedPreferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
                            SharedPreferences.Editor data = sharedPreferences.edit();
                            data.putString(Key, user.getName());
                            data.putString(Key2, city);
                            data.commit();
                            Toast.makeText(getApplicationContext(), "Succesful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
            }
        });
    }
}
