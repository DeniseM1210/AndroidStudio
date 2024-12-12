package controlador;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class AnalizadorJSON {
    private InputStream is = null;
    private OutputStream os = null;
    private JSONObject jsonObject = null;
    private HttpURLConnection conexion = null;
    private URL url;

    //codigo para la peticion HTTP
    public JSONObject peticionHTTP(String direccionURL, String metodo, ArrayList<String> datos){
        //Cadena JSON {"nc":"12345", "n":"Antony", "pAp":"Stark"}...
        String cadenaJSON = "";
        if(datos.size() == 1){
            cadenaJSON = "{\"nc\":\""+ datos.get(0) + "\"}";
        }else{
            cadenaJSON = "{\"nc\":\"" + datos.get(0) + "\",\"n\":\"" + datos.get(1) + "\",\"pAp\":\"" + datos.get(2) + "\",\"sAp\":\"" + datos.get(3) + "\",\"s\":\"" + datos.get(4) + "\",\"c\":\"" + datos.get(5) + "\",\"f\":\"" + datos.get(6) + "\",\"t\":\"" + datos.get(7) + "\"}";
        }


        try {
            url = new URL(direccionURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activar el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de envio
            conexion.setRequestMethod(metodo);

            //indicar el tamaño preestablecido o fijo de la cadena enviar
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //establecer el formato de la peticion
            conexion.setRequestProperty("Content-Type","application/x-www.form-urlencoded");

            //preparar el envio de la peticion
            os = new BufferedOutputStream(conexion.getOutputStream());
            os.write(cadenaJSON.getBytes());
            os.flush();
            os.close();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //recibir respuesta (RESPONSE)
        try {
            is = new BufferedInputStream(conexion.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cadena = new StringBuilder();

            String fila = null;
            while((fila = br.readLine()) != null){
                cadena.append(fila+"\n");
            }
            is.close();
            try {
                jsonObject = new JSONObject(String.valueOf(cadena));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
}
