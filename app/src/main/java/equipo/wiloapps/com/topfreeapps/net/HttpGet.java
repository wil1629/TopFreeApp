package equipo.wiloapps.com.topfreeapps.net;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by WILO on 11/04/2016.
 */
public class HttpGet {

    public static final String TAG = "HttpGet";

    public HttpGet(){}

    public String processDownloadJSON(){

        boolean statusProcess = false;
        URL url = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder=new StringBuilder();

        try {
            url = new URL(ConectionUrl.URL_TOP_FREE_APP);
            connection= (HttpURLConnection) url.openConnection ();
            connection.setRequestProperty ("User-Agent", "Mozilla/5.0" + "(Linux; Android 1.5; es-ES)");
            connection.setReadTimeout (ConectionUrl.TIME_OUT_CONNECTION);
            connection.setConnectTimeout (ConectionUrl.TIME_OUT_CONNECTION);
            new Thread (new InterruptThread (Thread.currentThread (),connection));
            if(connection.getResponseCode () == HttpURLConnection.HTTP_OK){
                in = new BufferedInputStream (connection.getInputStream ());
                reader = new BufferedReader (new InputStreamReader (in));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                //statusProcess = new ProcessJSON ().processJSONData (stringBuilder.toString ());
            }else{
                Log.d (TAG,"Conexion respuesta: " + connection.getResponseCode ());
            }
        } catch (MalformedURLException e) {
            Log.d (TAG,"Mal formada URL, Excepcion ");
        } catch (IOException e) {
            Log.d (TAG,"Conexion fallida, Excepcion");
        }finally {
            if(reader != null){
                try {
                    reader.close ();
                } catch (IOException e) {
                    Log.d (TAG,"Fallo al cerrar el bufferedReader, Excepcion");
                }
            }
            if(in != null){
                try {
                    in.close ();
                } catch (IOException e) {
                    Log.d (TAG,"Fallo al cerrar el inputStream, Excepcion");
                }
            }
            if(connection != null)connection.disconnect ();

        }
        return stringBuilder.toString ();
    }

}
