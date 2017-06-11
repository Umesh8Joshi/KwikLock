package com.umesh.kwiklock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public Button but1;
    public EditText eUsername;
    public EditText ePassword;

    public void init() {
        but1 = (Button)findViewById(R.id.bLogin);
        eUsername = (EditText)findViewById(R.id.eUsername);
        ePassword = (EditText)findViewById(R.id.ePassword);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eUsername.getText().toString().equals("admin") && ePassword.getText().toString().equals("1234")) {
                    Intent drive = new Intent(MainActivity.this, DeviceList.class);
                    startActivity(drive);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

}
