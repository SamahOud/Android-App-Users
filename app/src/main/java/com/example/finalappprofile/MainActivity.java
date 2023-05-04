package com.example.finalappprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends OptionsMenu implements View.OnClickListener {
    private TextView txtName,txtPassword,txtPoints;
    private Button btnLogin, btnProfile;

    private boolean flag = true;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
        txtPoints = findViewById(R.id.txtPoints);
        btnLogin = findViewById(R.id.btnLogin);
        btnProfile=findViewById(R.id.btnProfile);
        btnLogin.setOnClickListener(this);
        btnProfile.setOnClickListener(this);

        sp = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        flag = sp.getBoolean("flag:",true);

        if (flag) {
            txtName.setText("");
            txtPassword.setText("");
            txtPoints.setText("");
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("flag:",flag);
        }
        else {
            readData();
        }
    }

    @Override
    public void onClick(View view) {
        final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.text);
        CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
        final Toast toast = new Toast(getApplicationContext());

        Intent intent;
        if (view == btnProfile) {
            intent = new Intent(this,Profile.class);
            startActivityForResult(intent,1);
        }
        else if (view == btnLogin){
            sp = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            flag = sp.getBoolean("flag:",true);

            if (flag) {
                text.setText("you have to fill up and save your details!");
                lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

                intent = new Intent(this,Profile.class);
                startActivityForResult(intent,1);
            }
            else{
                intent = new Intent(this,Game.class);
                startActivityForResult(intent,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            flag = false;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("flag:",flag);
            editor.apply();
            flag = sp.getBoolean("flag:",true);
            readData();
        }
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK)
            readData();
    }

    public void readData(){
        sp = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String name = sp.getString("userName:", "");
        String password = sp.getString("Password:", "");
        String points = sp.getString("points:", "0");
        txtName.setText("Welcome  "+ name);
        txtPassword.setText("your password: "+ password);
        txtPoints.setText("you make  "+ points + "  Points");
    }
}