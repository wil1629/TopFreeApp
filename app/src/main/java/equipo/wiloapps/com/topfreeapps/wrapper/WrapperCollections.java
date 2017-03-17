package equipo.wiloapps.com.topfreeapps.wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import equipo.wiloapps.com.topfreeapps.models.App;

/**
 * Created by WILO on 11/04/2016.
 */
public class WrapperCollections {
    public static final String TAG = "WrapperCollections";

    private static WrapperCollections sWrapperCollections = new WrapperCollections ();
    private List<App> mListApps = new ArrayList<App> ();
    private List<String> mListCategorias = new ArrayList<String> () ;

    private boolean mModeUpData = false;

    private WrapperCollections () {
    }

    public static WrapperCollections getInstancia () {
        return sWrapperCollections;
    }

    //<editor-fold desc="Getters">
    public List<App> getListApps () {
        return mListApps;
    }

    public List<String> getListCategorias () {
        return mListCategorias;
    }

    public boolean getModeUpData(){
        return mModeUpData;
    }
    //</editor-fold>

    //<editor-fold desc="Setters Agregar a las Listas">
    public void setListApps (List<App> listApps) {
        mListApps.addAll (listApps);
    }

    public void setListApps (App app){
        mListApps.add (app);
    }

    public void setListCategorias (HashSet<String> listCategorias) {
        mListCategorias.addAll (listCategorias);
    }

    public void setListCategorias(String categoria ){
        mListCategorias.add (categoria);
    }

    public void setModeUpData(boolean modeUpData){
        mModeUpData = modeUpData;
    }
    //</editor-fold>

    public void emptyCollections(){
        if(!mListCategorias.isEmpty ())mListCategorias.clear ();
        if(!mListApps.isEmpty ())mListApps.clear ();
    }

    public boolean isEmptyCollections(){
        return mListApps.isEmpty () & mListCategorias.isEmpty ();
    }
}
