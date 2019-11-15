package com.example.apps1t.diariodefinitivo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText text;

    Button buttonAdd, buttonBack;


    //Editar texto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        text = findViewById(R.id.text);
        text.setText(intent.getStringExtra("aparecer"));
    }

    //Onclick para volver atrás
    public void goBack(View v){
        Button buttonBack =(Button) findViewById(R.id.buttonBack);
        //Cuando pulse el boton se cierra el activity
        if(buttonBack.isClickable()){
            finish();
        }
    }

    //Onclick para añadir el posts al array del diario
    public void addPost(View v){
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);

        String texto = text.getText().toString();

        //No podra añadir tarea si el texto esta vacio
        if(texto.isEmpty()){
            return;
        }
        //Cuando pulse el botón se cerrará el activity y se añadirá el string al array
        if(buttonAdd.isClickable()) {
            Intent intentResultado = new Intent();
            intentResultado.putExtra("resultado", texto);
            setResult(RESULT_OK, intentResultado);
            finish();
        }
    }
}
