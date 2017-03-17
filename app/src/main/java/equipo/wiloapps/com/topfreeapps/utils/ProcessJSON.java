package equipo.wiloapps.com.topfreeapps.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import equipo.wiloapps.com.topfreeapps.wrapper.WrapperCollections;
import equipo.wiloapps.com.topfreeapps.models.App;

/**
 * Created by WILO on 11/04/2016.
 */
public class ProcessJSON {

    public static final String TAG = "ProcessJSON";

    public ProcessJSON(){}

    public boolean processJSONData(String strJSON){

        final String LABEL = "label";
        final String FEED = "feed";
        final String ENTRY = "entry"; //array
        final String IM_NAME = "im:name";
        final String IM_IMAGE = "im:image"; //array
        final String SUMMARY = "summary";
        final String RIGHTS = "rights";
        final String TITLE = "title";
        final String ATTRIBUTES = "attributes";
        final String LINK = "link";
        final String HREF = "href";
        final String CATEGORY = "category";
        final int INDICE_LINK_IMAGE = 2;

        try {
            JSONObject objectJSON = new JSONObject(strJSON).getJSONObject (FEED);
            JSONArray  arrayEntry = objectJSON.getJSONArray (ENTRY);

            String category;
            String name;
            String urlImage;
            String nameImage;
            String linkApp;
            String summary;
            String rights;

            int cantidadEntry = arrayEntry.length ();
            HashSet<String> listCategorias=new HashSet<String> ();
            List<App> listApps = new ArrayList<App> ();

            for (int i = 0; i < cantidadEntry; i++) {
                JSONObject tempObjectJSON = arrayEntry.getJSONObject (i);

                category = tempObjectJSON.getJSONObject (CATEGORY).getJSONObject (ATTRIBUTES).getString (LABEL);
                name = tempObjectJSON.getJSONObject (TITLE).getString (LABEL);
                urlImage = tempObjectJSON.getJSONArray (IM_IMAGE).getJSONObject (INDICE_LINK_IMAGE).getString (LABEL);
                nameImage = tempObjectJSON.getJSONObject (IM_NAME).getString (LABEL);
                linkApp = tempObjectJSON.getJSONObject (LINK).getJSONObject (ATTRIBUTES).getString (HREF);
                summary = tempObjectJSON.getJSONObject (SUMMARY).getString (LABEL);
                rights = tempObjectJSON.getJSONObject (RIGHTS).getString (LABEL);

                listCategorias.add (category);
                listApps.add (new App (category,name,urlImage,nameImage,linkApp,summary,rights));
            }

            WrapperCollections.getInstancia ().emptyCollections ();
            WrapperCollections.getInstancia ().setListApps (listApps);
            WrapperCollections.getInstancia ().setListCategorias ("Apps");//Primera categoria para ver todas las apps
            WrapperCollections.getInstancia ().setListCategorias (listCategorias);
            Log.d (TAG,"cantidad apps:"+ listApps.size ()+" cantidad categorias:"+ listCategorias.size ());
            return true;

        } catch (JSONException e) {
            Log.d (TAG,"No se pudo analizar el JSONObject");
        }

        return false;
    }

}
