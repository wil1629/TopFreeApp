package equipo.wiloapps.com.topfreeapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;

public class About extends AppCompatActivity {
    public static final String TAG = "About";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        new ScreenDimens (this).setFullAndOrLandScapeScreen ();
        setContentView (R.layout.activity_about);
        Log.d (TAG,"onCreate");
    }

    @Override
    protected void onStop () {
        super.onStop ();
        Log.d (TAG,"onstop");
    }

    @Override
    protected void onPostResume () {
        super.onPostResume ();
        Log.d (TAG,"onpostresume");
    }

    @Override
    protected void onPause () {
        super.onPause ();
        Log.d (TAG,"onpause");
    }

    @Override
    protected void onResume () {
        super.onResume ();
        Log.d (TAG,"onresume");
    }

    @Override
    protected void onStart () {
        super.onStart ();
        Log.d (TAG,"onstart");
    }

    @Override
    protected void onRestart () {
        super.onRestart ();
        Log.d (TAG,"onrestart");
    }

}