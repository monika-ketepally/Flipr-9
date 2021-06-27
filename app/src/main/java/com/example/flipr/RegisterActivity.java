package com.example.flipr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DataBaseHelper openhelper;
    SQLiteDatabase db;
    Button reg;
    EditText firstName,lastName,password,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      //  getWindow().getDecorView().setBackgroundColor(Color.parseColor("#000033"));

        openhelper = new DataBaseHelper(this);
        firstName = (EditText) findViewById(R.id.editText1);
        lastName = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
        phone = (EditText) findViewById(R.id.editText5);
        reg = (Button) findViewById(R.id.button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db = openhelper.getWritableDatabase();
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String pass = password.getText().toString();
                String em = email.getText().toString();
                String ph = phone.getText().toString();
                boolean isInserted = openhelper.insertdata(fname,lname,pass,em,ph);
                if(isInserted) {
                    Toast.makeText(getApplicationContext(), "registered successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Looks like you have entered something wrong,check and refill the details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}