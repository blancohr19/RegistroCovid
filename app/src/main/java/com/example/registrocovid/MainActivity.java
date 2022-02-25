package com.example.registrocovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.txt_user);
        pass = findViewById(R.id.txt_pass);

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.contains("UserAdmin")){
            Intent intent1 = new Intent(this,Registros.class);
            startActivity(intent1);
            finish();
        }
    }

    public void guardarDatos(View v) {
        String usuario = user.getText().toString();
        String passw = pass.getText().toString();

        if (usuario.equals("") || passw.equals("")) {
            Toast.makeText(this, "Ingrese Todos los Datos", Toast.LENGTH_LONG).show();
        } else {
            editor.putString("UserAdmin", usuario);
            editor.putString("PassAdmin", passw);
            editor.commit();

            Intent intent1 = new Intent(this, Registros.class);
            startActivity(intent1);
            finish();
        }
    }
}