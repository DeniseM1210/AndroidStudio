package com.example.proyecto;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends Activity {
    private RecyclerView recyclerView;

    private AlumnoAdapter adapter;
    @Override


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        EditText filtro = findViewById(R.id.filtro);
        Intent intent = getIntent();
        filtro.setText(intent.getStringExtra("filtro"));


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        filtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intent intent = new Intent(ActivityConsultas.this, ActivityConsultas.class);
                intent.putExtra("filtro", filtro.getText().toString());
                startActivity(intent);



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork())!=null){
            String url = "http://10.0.2.2:81/Semestre_Ago_Dic_2024/ProyectoTutorias_2024/api_rest_android_escuela/api_mysql_consultas.php";
            String urlnc = "http://10.0.2.2:81/Semestre_Ago_Dic_2024/ProyectoTutorias_2024/api_rest_android_escuela/api_mysql_bajas.php";
            String metodo = "POST";
            ArrayList datos = new ArrayList();
            datos.add(filtro.getText().toString());



            AnalizadorJSON analizadorJSON = new AnalizadorJSON();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = analizadorJSON.peticionHTTP(url,metodo,datos);
                    List<Alumno> listaAlumnos = JsonUtil.obtenerListaAlumnos(jsonObject);

                    recyclerView = findViewById(R.id.recyclerView);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Configuración del RecyclerView en el hilo principal

                            adapter = new AlumnoAdapter(listaAlumnos);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityConsultas.this));
                            recyclerView.setAdapter(adapter);

                            adapter.setOnButtonClickListener(new AlumnoAdapter.OnButtonClickListener() {
                                @Override
                                public void onButtonClick(Alumno alumno, int position) {
                                    // Acción 1: Ejecución en un hilo separado
                                    ArrayList<String> datosnc = new ArrayList<>();
                                    datosnc.add(alumno.getNc());

                                    new Thread(() -> {
                                        analizadorJSON.peticionHTTP(urlnc, metodo, datosnc);
                                        runOnUiThread(() -> recreate()); // Reiniciar la actividad
                                    }).start();
                                }

                                @Override
                                public void onButtonClick2(Alumno alumno, int position) {
                                    // Acción 2: Abrir una nueva actividad
                                    Intent intent = new Intent(ActivityConsultas.this, ActivityCambios.class);
                                    intent.putExtra("nc", alumno.getNc());
                                    intent.putExtra("n", alumno.getN());
                                    intent.putExtra("pAp", alumno.getpAp());
                                    intent.putExtra("sAp", alumno.getsAp());
                                    intent.putExtra("s", alumno.getS());
                                    intent.putExtra("c", alumno.getC());
                                    intent.putExtra("f", alumno.getF());
                                    intent.putExtra("t", alumno.getT());
                                    startActivity(intent);
                                }

                            });



                        }
                    });
                    try {
                        String res = jsonObject.getString("res");
                        if(res.equals("exito")){

                            Log.i("MSJ->","correcto");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(),"consulta correcta",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

        }else{
            Log.e("MSJ->","Error en la red(WiFi)");
        }





    }




}
