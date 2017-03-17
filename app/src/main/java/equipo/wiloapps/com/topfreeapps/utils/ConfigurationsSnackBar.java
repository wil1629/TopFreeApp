package equipo.wiloapps.com.topfreeapps.utils;

import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * Created by WILO on 14/04/2016.
 */
public class ConfigurationsSnackBar {

    public ConfigurationsSnackBar(){}

    public void configurationSnackBark(Snackbar snackbar){
        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
        snackbarText.append("OFFLINE");
        snackbarText.setSpan(new ForegroundColorSpan (0xFFFF0000),0 , snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbarText.setSpan(new StyleSpan (android.graphics.Typeface.BOLD), 0, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbar.setText (snackbarText);
    }
}
