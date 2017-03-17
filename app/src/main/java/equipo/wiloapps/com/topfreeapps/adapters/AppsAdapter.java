package equipo.wiloapps.com.topfreeapps.adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import equipo.wiloapps.com.topfreeapps.R;
import equipo.wiloapps.com.topfreeapps.models.App;
import equipo.wiloapps.com.topfreeapps.receiver.EventItemViewApp;
import equipo.wiloapps.com.topfreeapps.utils.SetSaveImage;

/**
 * Created by WILO on 12/04/2016.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {
    public static final String TAG = "AppsAdapter";

    private final Context mContext;
    private final List<App> mListaApps = new ArrayList<App> ();

    private final EventItemViewApp mEventItemViewApp;

    public AppsAdapter (Context context, List<App> listaApps, EventItemViewApp eventItemViewApp) {
        mContext = context;
        mListaApps.addAll (listaApps);
        mEventItemViewApp = eventItemViewApp;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (mContext).inflate (R.layout.layout_apps, parent, false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder (final ViewHolder holder, int position) {
        final App app = mListaApps.get (position);

        holder.tvTituloLayoutApp.setText (app.getName ().split ("-")[0].split (",")[0]);
        new SetSaveImage (mContext, app, holder.ivImagenLayoutApp).executeSetSaveImage ();
    }

    @Override
    public int getItemCount () {
        return mListaApps.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTituloLayoutApp;
        private TextView tvPrecioLayoutApp;
        private ImageView ivImagenLayoutApp;

        public ViewHolder (View view) {
            super (view);
            view.setOnClickListener (this);
            ivImagenLayoutApp = (ImageView) view.findViewById (R.id.ivImagenLayoutApps);
            tvTituloLayoutApp = (TextView) view.findViewById (R.id.tvTituloLayoutApps);
            tvPrecioLayoutApp = (TextView) view.findViewById (R.id.tvPrecioLayoutApps);
        }

        @Override
        public void onClick (View v) {
            //int limitDer = v.getRootView ().getWidth ();
            //ObjectAnimator animator = ObjectAnimator.ofFloat (v,"alpha",0f,1f);
            //animator.setInterpolator (new AccelerateInterpolator (4));
            //animator.addListener (new ListenerAnimator (v));
            //animator.start ();

            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator (mContext,R.animator.anim_app_fade_scale);
            animatorSet.setTarget (v);
            animatorSet.addListener (new ListenerAnimator (v));
            animatorSet.start ();

            //mEventItemViewApp.getAppItemSelection (v,mListaApps);

            /*final FrameLayout viewParent = (FrameLayout) v.getRootView ().getRootView ();

            final FrameLayout fl = new FrameLayout (mContext);
            fl.setLayoutParams (new FrameLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
            fl.setBackgroundResource (R.drawable.circle_explode_app);
           // fl.setAlpha (0);
            viewParent.addView (fl);
            Animation animacion= AnimationUtils.loadAnimation(mContext,R.anim.explode_app);
            //ScaleAnimation sa= new ScaleAnimation (0,2000,0,2000,0.5f,0.5f);

            AnimationSet set = new AnimationSet (true);
            set.addAnimation (animacion);
            fl.startAnimation (set);

            set.setAnimationListener (new Animation.AnimationListener () {
                @Override
                public void onAnimationStart (Animation animation) {

                }

                @Override
                public void onAnimationEnd (Animation animation) {
                    viewParent.removeView (fl);
                }

                @Override
                public void onAnimationRepeat (Animation animation) {

                }
            });
*/
            //ObjectAnimator.ofFloat(frameLayout, "moveX", 0, 320).setDuration( 3 * 1000).start();
            //Animation animacion= AnimationUtils.loadAnimation(mContext,R.anim.explode_app);
            //v.startAnimation (animacion);
        }

        private class ListenerAnimator implements Animator.AnimatorListener {

            private View miView;

            public ListenerAnimator(View view){
                miView = view;
            }

            @Override
            public void onAnimationStart (Animator animation) {

            }

            @Override
            public void onAnimationEnd (Animator animation) {
                mEventItemViewApp.getAppItemSelection (miView,mListaApps);
            }

            @Override
            public void onAnimationCancel (Animator animation) {

            }

            @Override
            public void onAnimationRepeat (Animator animation) {

            }
        }

    }
}