package com.example.lak.gardenifycustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {

        EditText namebox,passbox,cpassbox,addbox,phonebox,emailbox,citybox;
        Button reg;
        DatabaseReference mDatabase;
        final String MyPrefs="UserPrefs";
         final String Key="Name";
         final String Key2="City";
        SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reg);

            namebox=(EditText) findViewById(R.id.regname);
            passbox=(EditText) findViewById(R.id.regpass);
            cpassbox=(EditText)findViewById(R.id.regcpass);
            reg=(Button) findViewById(R.id.registerbutton);
            addbox=(EditText) findViewById(R.id.regaddress);
            emailbox=(EditText)findViewById(R.id.regemail);
            citybox=(EditText) findViewById(R.id.editText7);
            phonebox=(EditText) findViewById(R.id.regphone);

            mDatabase = FirebaseDatabase.getInstance().getReference();




            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        String name=namebox.getText().toString();
                        String password=passbox.getText().toString();
                        String cpassword=cpassbox.getText().toString();
                        String address=addbox.getText().toString();
                        String phone=phonebox.getText().toString();
                        String city=citybox.getText().toString();
                        String email=emailbox.getText().toString();
                        if(!password.equals(cpassword)) {
                            Toast.makeText(getApplicationContext(),"Passwords do no match",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sharedPreferences=getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
                            SharedPreferences.Editor data=sharedPreferences.edit();
                            data.putString(Key,name);
                            data.putString(Key2,city);
                            data.commit();
                            User user = new User();
                            user.set(name,password,phone,address,email);
                            mDatabase.child(city.toUpperCase()).child("CUSTOMER").child(name).setValue(user);
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
}

