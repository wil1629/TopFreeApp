package equipo.wiloapps.com.topfreeapps.net;

import android.util.Log;

import java.net.HttpURLConnection;

/**
 * Created by WILO on 13/04/2016.
 */
public class InterruptThread implements Runnable {
    public static final String TAG = "InterruptThread";

    private Thread mParent;
    private HttpURLConnection mCon;

    public InterruptThread(Thread parent, HttpURLConnection con) {
        mParent = parent;
        mCon = con;
    }

    @Override
    public void run () {
        try {
            Thread.sleep(ConectionUrl.TIME_OUT_CONNECTION);
        } catch (InterruptedException e) {

        }
        Log.d (TAG,"Desconectar el thread parent");
        mCon.disconnect();
    }
}
