package equipo.wiloapps.com.topfreeapps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by WILO on 11/04/2016.
 */
public class GlobalReceiver extends BroadcastReceiver {
    public static final String TAG = "GlobalReceiver";

    public static final String EVENT_RECEIVER = "event_receiver";

    public  static final String OPERATION = "operation";
    public static final int  OPERATION_UP_DATA = 0;
    public static final int OPERATION_SELECCION_ITEM = 1;
    public static final int OPERATION_SELECCION_CATEGORY = 2;
    public static final int OPERATION_BACK_TO_CATEGORY = 3;

    public static final String BOOLEAN_EXTRA_NAME_ONLINE = "online";
    public static final String OBJETO_EXTRA_NAME_APP = "app";

    private final EventUpData mEventUpData;
    private final EventSeleccionCategoryApp mEventSeleccionCategoryApp;

    public GlobalReceiver(EventUpData eventUpData){
       this(eventUpData,null);
    }

    public GlobalReceiver (EventUpData eventUpData , EventSeleccionCategoryApp eventSeleccionCategoryApp) {
        mEventUpData = eventUpData;
        mEventSeleccionCategoryApp = eventSeleccionCategoryApp;
    }

    @Override
    public void onReceive (Context context, Intent intent) {

        if (intent != null) {
            final int operation = intent.getIntExtra (OPERATION,-1);

            switch (operation){

                case OPERATION_UP_DATA:{
                    mEventUpData.upDataFinish (intent.getBooleanExtra (BOOLEAN_EXTRA_NAME_ONLINE,true));
                    break;
                }
                case OPERATION_SELECCION_ITEM:{
                    mEventSeleccionCategoryApp.seleccionCategoryApp (OPERATION_SELECCION_ITEM,intent.getParcelableExtra (OBJETO_EXTRA_NAME_APP));
                    break;
                }
                case OPERATION_SELECCION_CATEGORY:{
                    mEventSeleccionCategoryApp.seleccionCategoryApp (OPERATION_SELECCION_CATEGORY,intent.getStringExtra (OBJETO_EXTRA_NAME_APP));
                    break;
                }
                case OPERATION_BACK_TO_CATEGORY:{
                    mEventSeleccionCategoryApp.seleccionCategoryApp (OPERATION_BACK_TO_CATEGORY,null);
                    break;
                }
                default: {
                    Log.d (TAG, "Operacion incorrecta");
                    break;
                }
            }

        }
    }
}
