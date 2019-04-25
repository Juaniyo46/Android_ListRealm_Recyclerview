package com.example.ejerciciolistas;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SaveActivity extends AppCompatActivity {

    Button button;
    Button atrasBtn;


    @Override
    public void onBackPressed (){
        Intent intent = new Intent(SaveActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserccion_datos);

        button = findViewById(R.id.anadir);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = findViewById(R.id.texto);
                EditText desc = findViewById(R.id.textDescription);
                String nameIntent = name.getText().toString();
                String descIntent = desc.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("sitio", nameIntent);
                intent.putExtra("descripcion", descIntent);
                setResult(2,intent);
                finish();
            }
        });

        atrasBtn = findViewById(R.id.atras);
        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
