package equipo.wiloapps.com.topfreeapps.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by WILO on 11/04/2016.
 */
public class ReadWriteDataPrivate {

    public static final String TAG = "ReadWriteDataPrivate";
    private final String FILE_NAME = "data.json";
    private final Context mContext;


    public ReadWriteDataPrivate(Context context){
        mContext = context;
    }

    public String readDataPrivate(){
        boolean status = false;
        FileInputStream fis = null;
        InputStreamReader in = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            fis = mContext.openFileInput (FILE_NAME);
            in = new InputStreamReader (fis);
            reader = new BufferedReader (in);

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (FileNotFoundException e) {
            Log.d (TAG,"Error al encontrar el archivo, Excepcion");
        } catch (IOException e) {
            Log.d (TAG,"Error al leer el bufferReader, Excepcion");
        }finally {
            if(reader != null){
                try {
                    reader.close ();
                } catch (IOException e) {
                    Log.d (TAG,"Error al cerrar el bufferedReader, Excepcion");
                }
            }
            if(in != null) {
                try {
                    in.close ();
                } catch (IOException e) {
                    Log.d (TAG,"Error al cerrar el inputStream, Excepcion");
                }
            }
            if(fis != null){
                try {
                    fis.close ();
                } catch (IOException e) {
                    Log.d (TAG,"Error al cerrar lectura del archivo, Excepcion");
                }
            }
        }

        return stringBuilder.toString ();
    }

    public boolean writeDataPrivate(String strJSON){

        boolean status = false;
        FileOutputStream fos = null;
        OutputStreamWriter output = null;

        try {
            fos = mContext.openFileOutput (FILE_NAME,Context.MODE_PRIVATE);
            output = new OutputStreamWriter (fos);
            output.write (strJSON);
            status = true;

        } catch (FileNotFoundException e) {
            Log.d (TAG,"Error al crear el archivo, Excepcion");
        } catch (IOException e) {
            Log.d (TAG,"Error al escribir en el archivo, Excepcion");
        }finally {

            if(output != null) try {
                output.close ();
            } catch (IOException e) {
                Log.d (TAG,"Error al cerrar la escritura del archivo, Excepcion");
            }
            if(fos != null) try {
                fos.close ();
            } catch (IOException e) {
                Log.d (TAG,"Error al cerrar el archivo, Excepcion");
            }
        }
    return status;
    }
}
