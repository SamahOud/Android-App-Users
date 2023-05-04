package com.example.finalappprofile;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenu extends AppCompatActivity {
    private boolean flag = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.login:
//                if (flag){
//                    Toast.makeText(this, "you have to fill up and save your details!", Toast.LENGTH_LONG).show();
//                    intent = new Intent(this,profile.class);
//                    startActivityForResult(intent,1);
//                }
//                else{
//                    intent = new Intent(this,game.class);
//                    startActivityForResult(intent,2);
//                }
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.setting:
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                return  true;
            case R.id.contact:
                intent = new Intent(this,Contact.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
