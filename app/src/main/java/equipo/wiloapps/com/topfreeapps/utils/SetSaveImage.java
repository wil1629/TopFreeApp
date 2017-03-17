package equipo.wiloapps.com.topfreeapps.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import equipo.wiloapps.com.topfreeapps.R;
import equipo.wiloapps.com.topfreeapps.models.App;
import equipo.wiloapps.com.topfreeapps.net.ConectionUrl;
import equipo.wiloapps.com.topfreeapps.net.InterruptThread;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;

/**
 * Created by WILO on 13/04/2016.
 */
public class SetSaveImage {

    public static final String TAG = "SetSaveImage";

    public static final String FORMAT_IMAGE =".png";
    public static final String DIR_IMAGENS = "imagens";
    private final Context mContext;
    private final App mApp;
    private final ImageView mImageView;

    public SetSaveImage(Context context, App app, ImageView imageView){
        mContext = context;
        mApp = app;
        mImageView = imageView;
    }

    public void executeSetSaveImage(){
        new ExecuteSetSaveImage ().execute ();
    }

    private class ExecuteSetSaveImage extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground (Void... params) {
            Bitmap bitmap = null;
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL (mApp.getUrlImage ());
                connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout (ConectionUrl.TIME_OUT_CONNECTION);
                connection.setConnectTimeout (ConectionUrl.TIME_OUT_CONNECTION);
                connection.connect();
                new Thread (new InterruptThread (Thread.currentThread (),connection));
                if(connection.getResponseCode () == connection.HTTP_OK) {
                    Log.d (TAG,"Conexion establecida");
                    bitmap = BitmapFactory.decodeStream (connection.getInputStream ());
                }else{
                    Log.d (TAG,"Conexion perdida");
                }
            } catch (MalformedURLException e) {
                Log.d (TAG,"URL Erronea");
            } catch (java.net.SocketTimeoutException e){
                Log.d (TAG,"Connection timeout");
            } catch (IOException e) {
                Log.d (TAG,"Connection fallida");
            } finally {
                if(connection != null){
                    connection.disconnect ();
                }
            }
            /* Conexion picasso cache
               Picasso picasso = Picasso.with (mContext);
               bitmap = picasso
                        .load (mApp.getUrlImage ())
                        .tag ("picasso")
                        .memoryPolicy (MemoryPolicy.NO_CACHE)
                        .networkPolicy (NetworkPolicy.NO_CACHE)
                        .get ();
            */
            return bitmap;
        }

        @Override
        protected void onPostExecute (Bitmap bitmap) {

            if(bitmap != null){
                sendResultUpImage (true);
                mImageView.setImageBitmap (bitmap);
                saveImagePrivate(bitmap);
            }else{
                sendResultUpImage (false);
                File dirImagens = mContext.getDir (DIR_IMAGENS, Context.MODE_PRIVATE);
                final File path = new File (dirImagens, mApp.getName () + FORMAT_IMAGE);

                Log.d (TAG,"Cargando modo Disco Private");
                Picasso picasso = Picasso.with (mContext);//Azul en la esquina significa cargado del disco(En este caso memoria interna)
                picasso.setIndicatorsEnabled (true);
                picasso.load (path)
                        .networkPolicy (NetworkPolicy.NO_CACHE)
                        .memoryPolicy (MemoryPolicy.NO_CACHE)
                        .error (R.drawable.carita_triste)
                        .into (mImageView);
            }
        }

        private void sendResultUpImage (boolean bool){
            Intent intent = new Intent (GlobalReceiver.EVENT_RECEIVER);
            intent.putExtra (GlobalReceiver.OPERATION,GlobalReceiver.OPERATION_UP_DATA);
            intent.putExtra (GlobalReceiver.BOOLEAN_EXTRA_NAME_ONLINE,bool);
            mContext.sendBroadcast (intent);
        }

        private void saveImagePrivate(final Bitmap bitmap){

            new Thread (new Runnable () {

                FileOutputStream fos = null;

                @Override
                public void run () {

                    File dirImagens = mContext.getDir (DIR_IMAGENS, Context.MODE_PRIVATE);
                    final File path = new File (dirImagens, mApp.getName () + FORMAT_IMAGE);

                    if (!path.exists ()){
                        try {
                            fos = new FileOutputStream (path);
                            bitmap.compress (Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush ();
                        } catch (FileNotFoundException e) {
                            Log.d (TAG,"Archivo no encontrado, excepcion");
                        } catch (IOException e) {
                            Log.d (TAG,"error flush , excepcion");
                        }finally {
                            if(fos != null){
                                try {
                                    fos.close ();
                                } catch (IOException e) {
                                    Log.d (TAG,"Error al cerrar el stream, excepcion");
                                }
                            }
                        }
                    }
                }
            }).start ();
        }
    }
}
