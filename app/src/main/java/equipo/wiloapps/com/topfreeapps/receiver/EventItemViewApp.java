package equipo.wiloapps.com.topfreeapps.receiver;

import android.view.View;

import java.util.List;

import equipo.wiloapps.com.topfreeapps.models.App;

/**
 * Created by WILO on 14/04/2016.
 */
public interface EventItemViewApp {
    void getAppItemSelection(View view, List<App> listAppCurrent);
}
