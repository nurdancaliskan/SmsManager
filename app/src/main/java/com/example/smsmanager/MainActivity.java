package com.example.smsmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextPhone,editTextMessage;
    Button btnSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = findViewById(R.id.editTextPhoneNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnSent = findViewById(R.id.btnSent);

        btnSent.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                //when permission is granted
                //create a method
                sendSMS();
            } else {
                //when permission is not granted
                //request for permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        //check condition
        if (requestCode == 100 && grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
            //permission is granted
            //call method
            sendSMS();
        }else {
            //when permission is denied
            //display toast message
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();

        }


    }
    private void sendSMS() {
        //get value from edittext
        String phone = editTextPhone.getText().toString();
        String message = editTextMessage.getText().toString();

        //check condition if string is empty or not
        if (!phone.isEmpty() && !message.isEmpty()) {
            //initalize SMS Manager
            SmsManager smsManager = SmsManager.getDefault();
            //send message
            smsManager.sendTextMessage(phone,null,message,null,null);
            //display Toast message
            Toast.makeText(this,"Sms Sent Successfully ",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Please enter phone and message",Toast.LENGTH_SHORT).show();

        }
    }
}