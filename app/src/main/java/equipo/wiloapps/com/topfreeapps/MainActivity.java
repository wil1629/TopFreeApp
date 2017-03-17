package equipo.wiloapps.com.topfreeapps;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import equipo.wiloapps.com.topfreeapps.fragments.AppsFragment;
import equipo.wiloapps.com.topfreeapps.fragments.CategoriesFragment;
import equipo.wiloapps.com.topfreeapps.models.App;
import equipo.wiloapps.com.topfreeapps.net.service.IntentServiceData;
import equipo.wiloapps.com.topfreeapps.receiver.EventSeleccionCategoryApp;
import equipo.wiloapps.com.topfreeapps.receiver.EventUpData;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.utils.ConfigurationsSnackBar;
import equipo.wiloapps.com.topfreeapps.utils.ScreenDimens;
import equipo.wiloapps.com.topfreeapps.wrapper.WrapperCollections;

public class MainActivity extends AppCompatActivity implements EventUpData,EventSeleccionCategoryApp{

    public static final String TAG = "MainActivity";//MainActivity.class.getSimpleName();

    private GlobalReceiver mGlobalReceiver;

    private CoordinatorLayout clSnackAlerta;
    private Snackbar mSnackbar;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout flCaritaAnimada;
    private TextView tvTextoActionBar;

    //private Animation mAnimacionCarita;
    //private Animation mAnimacionCaritaExplode;
    //private ViewGroup rootView;
    //private View viewRoot;
    private FrameLayout flBase;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        new ScreenDimens (this).setFullAndOrLandScapeScreen ();
        setContentView (R.layout.activity_main);
        registerGlobalReceiver();
        initUI ();
        setUpDrawer ();
        loadCategoriesApps ();
        //rootView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        //viewRoot=getWindow ().getDecorView ();
    }

    private void registerGlobalReceiver () {
        mGlobalReceiver = new GlobalReceiver (this, this);
        MainActivity.this.registerReceiver (mGlobalReceiver,new IntentFilter (GlobalReceiver.EVENT_RECEIVER));
    }
    private void setUpData(){
        Intent intent = new Intent (MainActivity.this, IntentServiceData.class);
        startService (intent);
    }
    private void loadApps(String categoria){

        Bundle bundle = new Bundle();
        bundle.putString (AppsFragment.CATEGORIA_SELECCION,categoria);

        Fragment fragment = new AppsFragment ();
        fragment.setArguments (bundle);

        tvTextoActionBar.setText (categoria);
        mToolbar.setBackgroundResource (R.drawable.background_action_bar_frio);
        mDrawerLayout.setBackgroundResource (R.color.colorApps);
        cargarFragment (fragment);

    }

    private void loadCategoriesApps(){
        tvTextoActionBar.setText ("Categories");
        mToolbar.setBackgroundResource (R.drawable.background_action_bar);
        mDrawerLayout.setBackgroundResource (R.color.colorCategorias);
        cargarFragment (new CategoriesFragment ());
    }

    private void cargarFragment(Fragment fragment){
        if(!WrapperCollections.getInstancia ().getModeUpData ()
                & WrapperCollections.getInstancia ().isEmptyCollections ()){
            setUpData ();
        }
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.setCustomAnimations
                (R.animator.enter_fragment,R.animator.exit_fragment);
        fragmentTransaction.replace (R.id.flContenedorFragments,fragment);
        fragmentTransaction.commit ();
    }

    private void loadActivityApp(App app){
        Intent intent = new Intent (MainActivity.this,AppView.class);
        intent.putExtra (GlobalReceiver.OBJETO_EXTRA_NAME_APP,app);
        startActivity (intent);
        overridePendingTransition(R.anim.enter_activity_app, R.anim.exit_splash_fragment_app);
    }

    private void loadActivityAbout(){
        Intent intent = new Intent (MainActivity.this, About.class);
        startActivity (intent);
        overridePendingTransition(R.anim.anim_activity_about_in, R.anim.anim_fragment_about_out);
    }

    private void initUI(){
        flBase =(FrameLayout)findViewById (R.id.flBase);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSnackbar = Snackbar.make (findViewById (R.id.navigation_drawer_layout),"",Snackbar.LENGTH_INDEFINITE).
                setAction ("TAP", new View.OnClickListener () {
                    @Override
                    public void onClick (View v) {
                        //TODO:: Implementar alguna action cuando se esta offline
                    }
                });
        new ConfigurationsSnackBar ().configurationSnackBark (mSnackbar);
    }

    private void setUpDrawer(){

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_action_bar, null);
        tvTextoActionBar = (TextView)viewActionBar.findViewById (R.id.tvTitleActioBar);
        mActionBar.setCustomView (viewActionBar,new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.list_layers_y);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,R.string.drawer_close);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout.addDrawerListener (mDrawerToggle);
        mDrawerLayout.addDrawerListener (new DrawerListenerLocal ());
        mDrawerToggle.syncState();
        setupNavigationDrawerContent(navigationView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_categories_apps:
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                loadCategoriesApps();
                                break;
                            case R.id.item_navigation_drawer_apps_free:
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                loadApps("Apps");
                                break;
                            case R.id.item_navigation_drawer_about_app:
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                loadActivityAbout ();
                                break;
                        }
                        return true;
                    }
                });
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
    public void upDataFinish (boolean booleanExtra) {
        Log.d (TAG,"llego la señal ....");
        showOrHideSnackBar (booleanExtra);
    }

    @Override
    protected void onDestroy () {
        MainActivity.this.unregisterReceiver (mGlobalReceiver);
        Log.d (TAG,"onDestroyMain desregistrado");
        super.onDestroy ();
    }

    private class DrawerListenerLocal implements DrawerLayout.DrawerListener{


        @Override
        public void onDrawerSlide (View drawerView, float slideOffset) {
            flBase.setTranslationX (drawerView.getWidth ()*slideOffset);
            flBase.invalidate ();
            flBase.requestLayout ();
        }

        @Override
        public void onDrawerOpened (View drawerView) {

        }

        @Override
        public void onDrawerClosed (View drawerView) {

        }

        @Override
        public void onDrawerStateChanged (int newState) {

        }
    }

    @Override
    public void seleccionCategoryApp (int tipoSeleccion, Object valueObject) {

        switch (tipoSeleccion){
            case GlobalReceiver.OPERATION_SELECCION_ITEM:{
                loadActivityApp ((App)valueObject);
                break;
            }
            case GlobalReceiver.OPERATION_SELECCION_CATEGORY:{
                loadApps ((String)valueObject);
                break;
            }
            case GlobalReceiver.OPERATION_BACK_TO_CATEGORY: {
                loadCategoriesApps ();
                break;
            }
            default:{
                Log.d (TAG,"No existe la operacion");
                break;
            }
        }
    }
}

 /*if(getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_XHIGH){}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            //HONEYCOMB Y SUPERIOR
        }else{
            //VERSIONES INFERIORES A HONEYCOMB
        }*/