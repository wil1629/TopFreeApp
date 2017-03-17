package equipo.wiloapps.com.topfreeapps.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import equipo.wiloapps.com.topfreeapps.R;
import equipo.wiloapps.com.topfreeapps.adapters.CategoriesAdapter;
import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by WILO on 12/04/2016.
 */
public class CategoriesFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private CategoriesAdapter mCategoriesAdapter;

    public CategoriesFragment () {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.content_recycler,container,false);
        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
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

        mCategoriesAdapter = new CategoriesAdapter (getActivity ());

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mCategoriesAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setFirstOnly(true);
        scaleAdapter.setInterpolator(new OvershootInterpolator (4f));
        mRecyclerView.setAdapter(scaleAdapter);

    }
}
