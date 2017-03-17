package equipo.wiloapps.com.topfreeapps;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import equipo.wiloapps.com.topfreeapps.net.ConectionUrl;
import equipo.wiloapps.com.topfreeapps.net.service.IntentServiceData;
import equipo.wiloapps.com.topfreeapps.receiver.EventUpData;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.utils.ConfigurationsSnackBar;
import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;

public class Splash extends AppCompatActivity implements EventUpData{

    public static final String TAG = "Splash";

    private static final int REQUEST_CODE_V6 = 16;

    private BroadcastReceiver mGlobalReceiver;

    private FrameLayout flPunta;
    private CoordinatorLayout clSnack;
    private TextView tvSubTitleOver;
    private Snackbar mSnackbar;


    @Override
    protected void onPostCreate (@Nullable Bundle savedInstanceState) {
        super.onPostCreate (savedInstanceState);

    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        new ScreenDimens (this).setFullAndOrLandScapeScreen ();
        setContentView (R.layout.activity_splash);

        permissionV6();

    }

    private void initLoad(){
        registerGlobalReceiver();
        initUI ();
        initAnimacion ();
        setUpData ();
    }

    private void permissionV6(){
        if (weHavePermissionToInternet()) {
            initLoad();
        } else {
            requestReadContactsPermissionFirst();
        }
    }

    private boolean weHavePermissionToInternet() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadContactsPermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            Toast.makeText(this, "Se necesita permiso para internet.", Toast.LENGTH_LONG).show();
            requestForResultContactsPermission();
        } else {
            requestForResultContactsPermission();
        }
    }

    private void requestForResultContactsPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_V6);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_V6 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso hecho", Toast.LENGTH_SHORT).show();
            initLoad();
        } else {
            Toast.makeText(this, "permiso denegado, la app no podra iniciar correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerGlobalReceiver () {
        mGlobalReceiver = new GlobalReceiver (this);
        Splash.this.registerReceiver (mGlobalReceiver,new IntentFilter (GlobalReceiver.EVENT_RECEIVER));
    }

    private void initUI(){

        flPunta = (FrameLayout)findViewById (R.id.flBrillo);
        clSnack = (CoordinatorLayout) findViewById (R.id.sbSnackInicio);
        tvSubTitleOver = (TextView)findViewById (R.id.textView1);
        mSnackbar = Snackbar.make (clSnack,"",Snackbar.LENGTH_INDEFINITE).
                setAction ("TAP", new View.OnClickListener () {
                    @Override
                    public void onClick (View v) {
                        Intent intent = new Intent (Splash.this,MainActivity.class);
                        startActivity (intent);
                        overridePendingTransition(R.anim.enter_main, R.anim.exit_splash_fragment_app);
                        finish ();
                    }
                });
    }

    private void initAnimacion(){
        FrameLayout frameLayout =(FrameLayout)findViewById (R.id.flTapa);
        FrameLayout frameLayout1 =(FrameLayout)findViewById (R.id.flBrillo);
        //ObjectAnimator.ofFloat(frameLayout, "moveX", 0, 320).setDuration( 3 * 1000).start();

        Animation animacion1=AnimationUtils.loadAnimation(this,R.anim.anim_guia_splash1);
        Animation animacion = AnimationUtils.loadAnimation(this,R.anim.anim_guia_splash);

        frameLayout.startAnimation (animacion);
        frameLayout1.startAnimation (animacion1);
        //frameLayout1.startAnimation (animacion);
        // AnimationSet set=new AnimationSet (true);
    }

    private void setUpData(){
        Intent intent = new Intent (Splash.this, IntentServiceData.class);
        startService (intent);
    }

    private void changeStatusAnimation(){
            flPunta.setBackgroundResource (R.drawable.list_layers_y);
            flPunta.postDelayed (new Runnable () {
                @Override
                public void run () {
                    flPunta.setBackgroundResource (R.drawable.list_layers_g);
                    mSnackbar.show ();
                }
            }, ConectionUrl.TIME_OUT_CONNECTION * 2);
    }

    @Override
    protected void onDestroy () {
        unregisterReceiver (mGlobalReceiver);
        super.onDestroy ();
    }

    @Override
    public void upDataFinish (boolean booleanExtra) {

        if(!booleanExtra)
            new ConfigurationsSnackBar ().configurationSnackBark (mSnackbar);
        changeStatusAnimation();
    }

}
