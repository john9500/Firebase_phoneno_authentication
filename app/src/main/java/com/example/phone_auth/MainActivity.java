package com.example.phone_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
EditText mobilenumber;
Button verif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobilenumber = findViewById(R.id.mobile);
        verif = findViewById(R.id.verify);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }

            private void register() {
                String phoneno = mobilenumber.getText().toString();
                Intent i = new Intent(getApplicationContext(),Verify.class);
                i.putExtra("Phone no",phoneno);
                startActivity(i);
            }
        });

    }
}