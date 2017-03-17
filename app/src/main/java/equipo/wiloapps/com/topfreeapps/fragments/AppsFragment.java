package equipo.wiloapps.com.topfreeapps.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import equipo.wiloapps.com.topfreeapps.R;
import equipo.wiloapps.com.topfreeapps.adapters.AppsAdapter;
import equipo.wiloapps.com.topfreeapps.models.App;
import equipo.wiloapps.com.topfreeapps.receiver.EventItemViewApp;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;
import equipo.wiloapps.com.topfreeapps.wrapper.WrapperCollections;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by WILO on 12/04/2016.
 */
public class AppsFragment extends Fragment implements EventItemViewApp{

    public static final String CATEGORIA_SELECCION = "categoria";
    private RecyclerView mRecyclerView;
    private AppsAdapter mAppsAdapter;
    private String mCategoria;

    public AppsFragment(){}


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.content_recycler,container,false);
        mCategoria = getArguments ().getString (CATEGORIA_SELECCION);
        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        setHasOptionsMenu (true);
        setUpRecyclerView (view);
    }

    private void setUpRecyclerView(View view){
        mRecyclerView = (RecyclerView)view.findViewById (R.id.recycler_view);

        ScreenDimens screenDimens = new ScreenDimens (getActivity ());
        int columns = 1;
        if(screenDimens.isTablet ())
            columns = screenDimens.getNumberColumns ();

        StaggeredGridLayoutManager staggeredGridLayoutManager;
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        List<App> listaApps = new ArrayList<App> ();
        listaApps.addAll (WrapperCollections.getInstancia ().getListApps ());
        if(mCategoria.equals ("Apps")) {
            mAppsAdapter = new AppsAdapter (getActivity (), listaApps,this);
        }
        else{
            List<App> listaAppsNew = new ArrayList<App> ();
            for (App app:listaApps){
                if(app.getCategory ().equals (mCategoria)){
                    listaAppsNew.add (app);
                }
            }
            mAppsAdapter = new AppsAdapter (getActivity (), listaAppsNew,this);
        }

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAppsAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setFirstOnly(true);
        scaleAdapter.setInterpolator(new OvershootInterpolator (3f));
        mRecyclerView.setAdapter(scaleAdapter);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reverse_activity:
                Intent intent = new Intent (GlobalReceiver.EVENT_RECEIVER);
                intent.putExtra (GlobalReceiver.OPERATION,GlobalReceiver.OPERATION_BACK_TO_CATEGORY);
                getActivity ().sendBroadcast (intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAppItemSelection (View view, List<App> listAppCurrent) {

        int viewPosition = mRecyclerView.getChildAdapterPosition (view);
        Intent intent = new Intent (GlobalReceiver.EVENT_RECEIVER);
        intent.putExtra (GlobalReceiver.OPERATION,GlobalReceiver.OPERATION_SELECCION_ITEM);
        intent.putExtra (GlobalReceiver.OBJETO_EXTRA_NAME_APP,listAppCurrent.get (viewPosition));
        getActivity ().sendBroadcast (intent);
    }
}
