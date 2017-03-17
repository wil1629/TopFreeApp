package equipo.wiloapps.com.topfreeapps.net.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import equipo.wiloapps.com.topfreeapps.wrapper.WrapperCollections;
import equipo.wiloapps.com.topfreeapps.net.ConectionUrl;
import equipo.wiloapps.com.topfreeapps.net.HttpGet;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.utils.ProcessJSON;
import equipo.wiloapps.com.topfreeapps.utils.ReadWriteDataPrivate;


public class IntentServiceData extends IntentService {

    public static final String TAG = "IntentServiceData";

    public IntentServiceData () {
        super ("IntentServiceData");
    }

    @Override
    protected void onHandleIntent (Intent intent) {

        sendResultUpData (setUpData () & WrapperCollections.getInstancia ().getModeUpData ());

    }

    private void sendResultUpData (boolean bool){
        Intent intent = new Intent (GlobalReceiver.EVENT_RECEIVER);
        intent.putExtra (GlobalReceiver.OPERATION,GlobalReceiver.OPERATION_UP_DATA);
        intent.putExtra (GlobalReceiver.BOOLEAN_EXTRA_NAME_ONLINE,bool);
        getApplicationContext ().sendBroadcast (intent);
    }

    private synchronized boolean setUpData(){
        boolean success = false;
        String strJSON = "";

        for(int i = 0;i < ConectionUrl.NUM_INTENTOS_CONEXION; i++) {
            strJSON = "";
            strJSON = new HttpGet ().processDownloadJSON ();
            if (!strJSON.isEmpty () && (success = new ProcessJSON ().processJSONData (strJSON))) {
                    WrapperCollections.getInstancia ().setModeUpData (true);
                    break;
            }
            try {
                wait (ConectionUrl.TIME_OUT_CONNECTION);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        if(success){
            writeDataPrivate (strJSON);
        }else {
            strJSON = readDataPrivate ();
            if (!strJSON.isEmpty () && (success = new ProcessJSON ().processJSONData (strJSON)))
                WrapperCollections.getInstancia ().setModeUpData (false);
        }
        return success;
    }

    private void writeDataPrivate(String strJSON){
        if(new ReadWriteDataPrivate (this.getApplicationContext ()).writeDataPrivate (strJSON)){
            Log.d (TAG,"Se escribio el archivo data.json exitosamente");
        }else
        {
            Log.d (TAG,"Error al crear el archivo data.json");
        }
    }

    private String readDataPrivate(){
        return new ReadWriteDataPrivate (this.getApplicationContext ()).readDataPrivate ();
    }
}
