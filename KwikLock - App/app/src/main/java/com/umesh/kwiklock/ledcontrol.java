package com.umesh.kwiklock;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Umesh on 09-05-2017.
 */

public class ledcontrol extends AppCompatActivity {
    Button On,Off,Discnt;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtconnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);
        setContentView(R.layout.activity_led_control);

        On = (Button)findViewById(R.id.button2);
        Off = (Button)findViewById(R.id.button3);
        Discnt =(Button)findViewById(R.id.button4);

        new ConnectBT().execute();

        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoor();
            }
        });

        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDoor();
            }
        });

        Discnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });
    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error"); }
        }
        finish();
    }
    private void closeDoor()
    {
        if (btSocket!=null)
        {
            try
            {
               btSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void openDoor(){
        if(btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledcontrol.this, "Connecting..","Please Wait");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtconnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispostivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispostivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    btSocket.connect();
                    isBtconnected = true;

                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtconnected = true;
            }
            progress.dismiss();
        }
    }
    }