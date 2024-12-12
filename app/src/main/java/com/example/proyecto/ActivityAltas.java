package com.example.proyecto;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import controlador.AnalizadorJSON;

public class ActivityAltas extends Activity {
    private EditText editTextDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);


        editTextDate = findViewById(R.id.altas_f); // Asegúrate de tener un EditText en tu XML

        // Crear un Calendar para obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();

        // Establecer un formato para la fecha (yyyy/MM/dd)
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        // Establecer un listener para el EditText
        editTextDate.setOnClickListener(v -> {
            // Mostrar un DatePickerDialog
            new DatePickerDialog(ActivityAltas.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // Establecer la fecha seleccionada en el EditText con el formato deseado
                    calendar.set(year, monthOfYear, dayOfMonth);
                    editTextDate.setText(dateFormat.format(calendar.getTime()));
                }
            },
                    calendar.get(Calendar.YEAR), // Año actual
                    calendar.get(Calendar.MONTH), // Mes actual
                    calendar.get(Calendar.DAY_OF_MONTH)).show(); // Día actual
        });

    }
    public void agregarAlumno(View v){
        //1) obtener los datos de la GUI
        //2) Crear una instancia de alumnos
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        EditText enc = findViewById(R.id.altas_nc);
        String nc = enc.getText().toString();

        EditText en = findViewById(R.id.altas_n);
        String n = en.getText().toString();

        EditText epAp = findViewById(R.id.altas_pAp);
        String pAp = epAp.getText().toString();

        EditText esAp = findViewById(R.id.altas_sAp);
        String sAp = esAp.getText().toString();

        Spinner ss = findViewById(R.id.altas_s);
        String s = ss.getSelectedItem().toString();

        Spinner sc = findViewById(R.id.altas_c);
        String c = sc.getSelectedItem().toString();


        EditText ef = findViewById(R.id.altas_f);
        String f = ef.getText().toString();



        EditText et = findViewById(R.id.altas_t);
        String t = et.getText().toString();



        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork())!=null){
            String url = "http://10.0.2.2:80/Semestre_Ago_Dic_2024/ProyectoTutorias_2024/api_rest_android_escuela/api_mysql_altas.php";
            String metodo = "POST";
            ArrayList datos = new ArrayList();
            datos.add(nc);
            datos.add(n);
            datos.add(pAp);
            datos.add(sAp);
            datos.add(s);
            datos.add(c);
            datos.add(f);
            datos.add(t);


            AnalizadorJSON analizadorJSON = new AnalizadorJSON();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = analizadorJSON.peticionHTTP(url,metodo,datos);
                    try {
                        String res = jsonObject.getString("alta");
                        if(res.equals("exito")){

                            Log.i("MSJ->","insercion correcta");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(),"Insercion Correcta",Toast.LENGTH_LONG).show();
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
