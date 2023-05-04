package com.example.finalappprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends OptionsMenu implements View.OnClickListener {
    private ImageView tabLocation;
    private Button btnCall, btnSms, btnMail;
    private EditText et_phone_number, et_sms;
    private EditText et_mail_address, et_mail_sub,et_mail_msg;
    private static final int LOCATION_CODE = 1;
    private static final int CALL_CODE = 2;
    private static final int SMS_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.text);
        CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
        final Toast toast = new Toast(getApplicationContext());

        switch (requestCode) {
            case LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    text.setText("Location permission is granted");
                    lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                } else {
                    text.setText("Location permission is declined");
                    lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                break;
        }
    }

    public void locationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_layout_id));
            TextView text = (TextView) layout.findViewById(R.id.text);
            CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
            final Toast toast = new Toast(getApplicationContext());

            text.setText("Location Permission already granted");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
        }
    }

    public void callPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {
            final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_layout_id));
            TextView text = (TextView) layout.findViewById(R.id.text);
            CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
            final Toast toast = new Toast(getApplicationContext());

            text.setText("Call Permission already granted");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            makeCall();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, CALL_CODE);
        }
    }
    public void makeCall(){
        Intent intent;
        String phoneNumber = et_phone_number.getText().toString();
        intent= new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }

    public void smsPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED) {
            final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_layout_id));
            TextView text = (TextView) layout.findViewById(R.id.text);
            CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
            final Toast toast = new Toast(getApplicationContext());

            text.setText("SMS Permission already granted");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            sendSms();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, SMS_CODE);
        }
    }

    public void sendSms(){
        String message = et_sms.getText().toString();
        String phoneNumber = et_phone_number.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("sms:"+phoneNumber));
        intent.putExtra("msg",message);
        smsManager.sendTextMessage(phoneNumber,null,message,null,null);
        startActivity(intent);
    }

    public void sendEmail(){
        String mailAddress = et_mail_address.getText().toString();
        String mailSub = et_mail_sub.getText().toString();
        String mailMsg = et_mail_msg.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + mailSub+ "&body=" + mailMsg + "&to=" + mailAddress);
        emailIntent.setData(data);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void initView(){
        tabLocation = findViewById(R.id.tabLocation);
        tabLocation.setOnClickListener(this);
        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        btnSms = findViewById(R.id.btnSms);
        btnSms.setOnClickListener(this);
        et_phone_number = findViewById(R.id.phone_input);
        et_sms = findViewById(R.id.sms_input);
        et_mail_address = findViewById(R.id.email_address);
        et_mail_sub = findViewById(R.id.email_sub);
        et_mail_msg= findViewById(R.id.email_msg);
        btnMail = findViewById(R.id.btnMail);
        btnMail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v == tabLocation){
            locationPermission();
        }
        else if( v == btnCall){
            callPermission();
        }
        else if ( v == btnSms){
            smsPermission();
        }
        else if ( v == btnMail){
            sendEmail();
            et_mail_address.setText("");
            et_mail_sub.setText("");
            et_mail_msg.setText("");
        }
    }
}