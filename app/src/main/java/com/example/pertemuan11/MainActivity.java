package com.example.pertemuan11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean logged_in = false;

    private SharedPreferences mSharedPref;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnSubmitLogin;

    private final String sharedPrefFile = "com.example.pertemuan11";

    private final String LOGIN_KEY = "loginkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnSubmitLogin = findViewById(R.id.btnSubmitLogin);

        mSharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        logged_in = mSharedPref.getBoolean(LOGIN_KEY, false);
        Intent intent = new Intent(MainActivity.this, dashboard.class);

        if (logged_in == true){
            startActivityForResult(intent,1);
        }

        btnSubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = String.valueOf(editTextUsername.getText());
                String password = String.valueOf(editTextPassword.getText());
                if (username.equals("test")&&password.equals("test")){
                    logged_in = true;
                    saveLoginStatus();
                    intent.putExtra("logged_in",true);
                    startActivityForResult(intent,1);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error Login");
                    alertDialog.setMessage("Username atau Password salah");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }
    private void saveLoginStatus(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(LOGIN_KEY, logged_in);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                logged_in = false;
                saveLoginStatus();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            }
        }
    }
}