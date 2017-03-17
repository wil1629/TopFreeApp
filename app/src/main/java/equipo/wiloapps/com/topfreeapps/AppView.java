package equipo.wiloapps.com.topfreeapps;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import equipo.wiloapps.com.topfreeapps.models.App;
import equipo.wiloapps.com.topfreeapps.receiver.EventUpData;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.utils.ConfigurationsSnackBar;
import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;
import equipo.wiloapps.com.topfreeapps.utils.SetSaveImage;

public class AppView extends AppCompatActivity implements EventUpData{

    public static final String TAG = "AppView";

    private ImageView ivImagenApp;
    private TextView tvNameApp;
    private TextView tvCategoryApp;
    private TextView tvLinkDownload;
    private TextView tvSummary;
    private TextView tvCopyRight;

    private Snackbar mSnackbar;
    private GlobalReceiver mGlobalReceiver;
    private App mApp;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        new ScreenDimens (this).setFullAndOrLandScapeScreen ();
        setContentView (R.layout.activity_app_view);
        Intent intent = getIntent ();
        mApp = (App)intent.getParcelableExtra (GlobalReceiver.OBJETO_EXTRA_NAME_APP);

        initUI();
        registerGlobalReceiver ();
        setValores();
    }

    private void registerGlobalReceiver () {
        mGlobalReceiver = new GlobalReceiver (this);
        AppView.this.registerReceiver (mGlobalReceiver,new IntentFilter (GlobalReceiver.EVENT_RECEIVER));
    }

    private void initUI(){

        ivImagenApp = (ImageView)findViewById (R.id.ivImagenApp);
        tvNameApp = (TextView)findViewById (R.id.tvNameApp);
        tvCategoryApp = (TextView)findViewById (R.id.tvCategoryApp);
        tvLinkDownload = (TextView)findViewById (R.id.tvLinkDownload);
        tvSummary = (TextView)findViewById (R.id.tvSummary);
        tvCopyRight = (TextView)findViewById (R.id.tvCopyRight);
        mSnackbar = Snackbar.make (findViewById (R.id.svScrollView),"",Snackbar.LENGTH_INDEFINITE).
                setAction ("TAP", new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        //TODO:: Implementar alguna action cuando se esta offline
                    }
                });
        new ConfigurationsSnackBar ().configurationSnackBark (mSnackbar);
    }

    private void setValores(){
        new SetSaveImage (this,mApp,ivImagenApp).executeSetSaveImage ();
        String base="<b>Link DownLoad: </b><a href=\"%s\">%s</a>";
        tvNameApp.setText (mApp.getName ());
        tvCategoryApp.setText (mApp.getCategory ());
        tvLinkDownload.setText (Html.fromHtml (String.format (base,mApp.getLinkApp (),mApp.getLinkApp ())));
        tvLinkDownload.setMovementMethod(LinkMovementMethod.getInstance());
        tvSummary.setText (mApp.getSummary ());
        tvCopyRight.setText (mApp.getRights ());
    }

    @Override
    public void upDataFinish (boolean booleanExtra) {
        Log.d (TAG,"llego la señal ....");
        showOrHideSnackBar (booleanExtra);
    }

    private void showOrHideSnackBar(boolean bool){
        Log.d (TAG,"showOrHideSnackBar");
        if(bool){
            if(mSnackbar.isShown ())
                mSnackbar.dismiss ();
        }
        else{
            if(!mSnackbar.isShown ())
                mSnackbar.show ();
        }
    }

    @Override
    protected void onDestroy () {
        AppView.this.unregisterReceiver (mGlobalReceiver);
        super.onDestroy ();
    }
}
