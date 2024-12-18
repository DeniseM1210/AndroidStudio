package com.example.proyecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static List<Alumno> obtenerListaAlumnos(JSONObject jsonObject) {
        List<Alumno> listaAlumnos = new ArrayList<>();
        try {
            JSONArray alumnosArray = jsonObject.getJSONArray("alumnos");
            for (int i = 0; i < alumnosArray.length(); i++) {
                JSONObject alumnoJSON = alumnosArray.getJSONObject(i);
                String nc = alumnoJSON.getString("nc");
                String n = alumnoJSON.getString("n");
                String pAp = alumnoJSON.getString("pAp");
                String sAp = alumnoJSON.getString("sAp");
                String s = alumnoJSON.getString("s");
                String c = alumnoJSON.getString("c");
                String f = alumnoJSON.getString("f");
                String t = alumnoJSON.getString("t");


                // Crear el objeto Alumno
                Alumno alumno = new Alumno(nc, n, pAp,sAp,s,c,f,t);
                listaAlumnos.add(alumno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlumnos;
    }
}
