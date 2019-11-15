package com.example.apps1t.diariodefinitivo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Variables
    String texto;
    int posicion;
    private Context text;

    ArrayList<String> postsDiario;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    ListView postsListView;

    String mydate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
        postsListView =(ListView) findViewById(R.id.postsListView);
        postsDiario = cargarLista();
        Log.d("****", postsDiario.toString());
        adapter = new ArrayAdapter<String>(this,R.layout.mylayout, postsDiario);
        postsListView.setAdapter(adapter);
        text =this;

        //Editar el elemento seleccionado del array
        postsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicionn, long id) {
                posicion = posicionn;
                Intent intent = new Intent(text, Main2Activity.class);

                //Guardar texto en la otra pantalla
                String  cogerText = postsDiario.get(posicionn);
                intent.putExtra("aparecer", cogerText);
                startActivityForResult(intent, 2);
            }
        });

        //Eliminar el elemento seleccionado del array manteniendo el click
        postsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Código para que se guarden el texto en la otra pantallla
                postsDiario.remove(position);
                adapter.notifyDataSetChanged();
                guardarLista(postsDiario);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Se añade el texto al array
        if (requestCode == 1) {
            if(resultCode == MainActivity.RESULT_OK){
                texto = data.getStringExtra("resultado");
                arrayAnadir(postsDiario, texto);
            }
        }
        //Para editar el texto
        if(requestCode ==2){
            if(resultCode ==MainActivity.RESULT_OK){
                String editarEntrada = data.getStringExtra("resultado");
                postsDiario.set(posicion, editarEntrada);
                adapter.notifyDataSetChanged();
            }
        }
        guardarLista(postsDiario);
    }

    //onClick para cambiar de activity
    public void anadirPost(View v){
        Button buttonAnadir =(Button) findViewById(R.id.buttonAnadir);
        if(buttonAnadir.isClickable()){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivityForResult(intent,1);
        }
    }

    //Añadir el texto al array del diario
    private void arrayAnadir(ArrayList<String> postsDiario, String texto){
        if(texto.isEmpty()){
            return;
        }
        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        postsDiario.add( "-----------------------------------------------------------------------------------" + "\n" + "|                         "
                + mydate + "                         |" + "\n" + "-----------------------------------------------------------------------------------" + "\n" + texto);
        adapter.notifyDataSetChanged();
    }

    //Guardar la lista de arrays
    private void guardarLista(ArrayList<String> textos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < textos.size(); i++) {
            editor.putString("text" + i, textos.get(i));
        }
        editor.putInt("longitud", textos.size());
        // Guardar el tamaño de la lista
        editor.commit();
    }

    //Cargar la lista de arrays
    private ArrayList<String> cargarLista() {
        ArrayList<String> textos = new ArrayList<>();
        // Obtener el tamaño de la lista
        int longitud = sharedPreferences.getInt("longitud", 0);
        // Obtener todos los textos
        for (int i = 0; i < longitud; i++) {
            String texto = sharedPreferences.getString("text" + i, "");
            textos.add(texto);
        }
        return textos;
    }
}
