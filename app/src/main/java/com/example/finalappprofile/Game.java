package com.example.finalappprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends OptionsMenu implements View.OnClickListener {
    private TextView tvMain, tvPoints,num1,num2;
    private EditText result;
    private Button btnNext,btnGameOver;
    private int counter = 0;
    private boolean flag = true;
    private SharedPreferences sp;
    int sum,x,y;
    String stResult,points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initView();
    }

    public void initView(){
        tvMain = findViewById(R.id.tvMain);
        tvPoints = findViewById(R.id.tvPoints);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.result);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnGameOver = findViewById(R.id.btnGameOver);
        btnGameOver.setOnClickListener(this);
        num1.setText(String.valueOf(rndNum()));
        num2.setText(String.valueOf(rndNum()));

        sp = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        // Reading Full Name from sharedPreferences + put it in MainText:
        String name = sp.getString("fullName:","");
        String birthday = sp.getString("Birthday:","");
        tvMain.setText("Welcome "+ name + "\n" + birthday);

        // Reading Points from sharedPreference + put it in PointsText:
        points = sp.getString("points:","0");
        counter = Integer.parseInt(points);
        tvPoints.setText("Points:  " + points);
    }

    public int rndNum() {
        return (int) (Math.random() * 100);
    }

    @Override
    public void onClick(View view) {
        if (view == btnNext){
            if (flag){
                workingOnBtnNext();
                flag = false;
            }
            else {
                workingOnBtnNext();
                flag = true;
            }
        }
        else if (view == btnGameOver){
            points = String.valueOf(counter);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("points:",points);
            editor.apply();
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
    public void workingOnBtnNext() {
        final View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.text);
        CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
        final Toast toast = new Toast(getApplicationContext());

        if (result.getText().toString().equals("")) {
            text.setText("You have to answer this quiz!");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

        stResult = result.getText().toString();
        x = Integer.parseInt(num1.getText().toString());
        y = Integer.parseInt(num2.getText().toString());
        sum = Integer.parseInt(stResult);

        if (x + y == sum) {
            text.setText("Correct :)");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

            counter += 5;
            points = String.valueOf(counter);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("points:",points);
            editor.apply();
            points = sp.getString("points:","");
            tvPoints.setText("Points:  " + String.valueOf(counter));
        }
        else {
            text.setText("Wrong !!");
            lyt_card.setCardBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

        num1.setText(String.valueOf(rndNum()));
        num2.setText(String.valueOf(rndNum()));
        result.setText("");
    }
}