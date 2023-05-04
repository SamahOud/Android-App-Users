package com.example.finalappprofile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Profile extends OptionsMenu implements View.OnClickListener {
    private ImageView tabImg;
    private EditText etName, etAddress, etUserName, etPswrd;
    private TextView txtBirth, tabGender, txtGender;
    private ImageView tabCalender;
    private RadioGroup radioGroup;
    private Button btnConfirm, btnSave;
    private String fullName,userName, password,address,birthday,gender;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();

    }

    public void showGenderDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.gender);
        dialog.setTitle("Gender");
        dialog.setCancelable(true);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        dialog.show();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = dialog.findViewById(radioId);
                String gender = radioButton.getText().toString();
                txtGender.setText(gender);
                dialog.cancel();
            }
        });
    }
    public void savingDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Saving your data...");
        alertDialog.setMessage("are you sure to save all your information?");
        alertDialog.setIcon(getDrawable(R.drawable.q_mark));
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                TextView text = (TextView) layout.findViewById(R.id.text);
                CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
                final Toast toast = new Toast(getApplicationContext());

                sp = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                fullName = etName.getText().toString();
                userName = etUserName.getText().toString();
                password = etPswrd.getText().toString();
                address = etAddress.getText().toString();
                birthday = txtBirth.getText().toString();
                gender = txtGender.getText().toString();
                if(fullName.equals("") || userName.equals("") || password.equals("") || address.equals("")
                        || birthday.equals("") || gender.equals("") || tabImg.getDrawable() == null) {
                    text.setText("you missed to write one or more of your Details!");
                    lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("fullName:",fullName);
                    editor.putString("userName:",userName);
                    editor.putString("Password:",password);
                    editor.putString("Address:",address);
                    editor.putString("Birthday:",birthday);
                    editor.putString("Gender:",gender);
                    editor.apply();

                    text.setText("your details are saved.");
                    lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();

                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                TextView text = (TextView) layout.findViewById(R.id.text);
                CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
                final Toast toast = new Toast(getApplicationContext());

                text.setText("your details have been LOST!");
                lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == tabImg){
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivityForResult(intent,1);
            }
        }
        else if (v == tabCalender){
            Calendar sysCalender = Calendar.getInstance();
            int year = sysCalender.get(Calendar.YEAR);
            int month = sysCalender.get(Calendar.MONTH);
            int day = sysCalender.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(this,new setDate(),year,month,day);
            datePicker.show();
        }
        else if (v == tabGender){
            showGenderDialog();
        }
        else if (v == btnConfirm){
        }
        else if (v == btnSave){
            savingDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                assert data != null;
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                tabImg.setImageBitmap(imageBitmap);
            }
        }
    }

    private class setDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            String str = dayOfMonth + "/" + month + "/" + year;
            txtBirth.setText(str);
        }
    }

    public void initView(){
        tabImg = findViewById(R.id.btnImg);
        tabImg.setOnClickListener(this);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etUserName = findViewById(R.id.etUserName);
        etPswrd = findViewById(R.id.etPswrd);
        txtBirth = findViewById(R.id.txtBirth);
        txtGender = findViewById(R.id.txtGender);
        txtBirth = findViewById(R.id.txtBirth);
        tabGender = findViewById(R.id.btnGender);
        tabGender.setOnClickListener(this);
        tabCalender = findViewById(R.id.btnCalender);
        tabCalender.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        readData();
    }
    public void readData(){
        sp = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        fullName = sp.getString("fullName:","");
        userName = sp.getString("userName:","");
        password = sp.getString("Password:","");
        address = sp.getString("Address:","");
        birthday = sp.getString("Birthday:","");
        gender = sp.getString("Gender:","");

        etName.setText(fullName);
        etUserName.setText(userName);
        etPswrd.setText(password);
        etAddress.setText(address);
        txtBirth.setText(birthday);
        txtGender.setText(gender);
    }
}