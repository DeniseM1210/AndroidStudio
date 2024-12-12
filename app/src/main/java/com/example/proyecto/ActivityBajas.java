package com.example.proyecto;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controlador.AnalizadorJSON;

public class ActivityBajas extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);
    }

    public void eliminarAlumno(View v){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        EditText enc = findViewById(R.id.caja_numc);
        String nc = enc.getText().toString();

        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork())!=null){
            String url = "http://10.0.2.2:80/Semestre_Ago_Dic_2024/ProyectoTutorias_2024/api_rest_android_escuela/api_mysql_bajas.php";
            String metodo = "POST";
            ArrayList datos = new ArrayList();
            datos.add(nc);

            AnalizadorJSON analizadorJSON = new AnalizadorJSON();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = analizadorJSON.peticionHTTP(url, metodo, datos);

                    try {
                        String res = jsonObject.getString("delete");
                        if (res.equals("exito")){
                            Log.i("MSJ->", "Eliminacion correcta");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Eliminacion correcta", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }else{
            Log.e("MSJ->", "Error en la red WIFI");
        }
    }
}
