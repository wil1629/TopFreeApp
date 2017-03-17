package equipo.wiloapps.com.topfreeapps.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by WILO on 14/04/2016.
 */
public class ScreenDimens{

    public static final String TAG = "ScreenDimens";

    private final float mMinDimensionInch = 5.5f; //para phablet y tablets, hay tablets de 5.8 pulgadas
    private final int mAnchoItemDP = 390;
    private final Activity mActivity;

    public ScreenDimens(Activity activity){
        mActivity = activity;
    }

    public void setFullAndOrLandScapeScreen () {
        mActivity.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setLandTablet ();
    }

    private void setLandTablet(){
        if(isTablet ()){
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            mActivity.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mActivity.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    public boolean isTablet(){
        if(((mActivity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) || getDimensionInches () >= mMinDimensionInch)
        {
            return true;
        }
        return false;
    }

    public int getNumberColumns(){
        return getNumberColumns (mAnchoItemDP);
    }

    public int getNumberColumns( int anchoItemDP ){
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int dpi = (int)metrics.xdpi;
        int columns = (160*width)/(dpi*anchoItemDP);

        return columns;
    }
    private double getDimensionInches() {

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int dpiY = (int) displayMetrics.ydpi;
        int dpiX = (int) displayMetrics.xdpi;
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        try
        {
            Point size = new Point();
            //Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);//para evitar la advertencia
            display.getRealSize (size);
            width = size.x;
            height = size.y;
        }
        catch (Exception e) {
            Log.d(TAG,"Error en getRealSize");
        }
        return (float)Math.sqrt(Math.pow(width/dpiX,2)+ Math.pow(height/dpiY,2));
    }

}
