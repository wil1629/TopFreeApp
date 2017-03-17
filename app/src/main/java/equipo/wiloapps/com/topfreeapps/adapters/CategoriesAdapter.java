package equipo.wiloapps.com.topfreeapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import equipo.wiloapps.com.topfreeapps.R;
import equipo.wiloapps.com.topfreeapps.receiver.GlobalReceiver;
import equipo.wiloapps.com.topfreeapps.wrapper.WrapperCollections;

/**
 * Created by WILO on 12/04/2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> mListaCategorias = new ArrayList<String> ();

    public CategoriesAdapter (Context context){
        mContext = context;
        mListaCategorias.addAll (WrapperCollections.getInstancia ().getListCategorias ());
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (mContext).inflate (R.layout.layout_categorias,parent,false);
        return  new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        final ViewHolder viewHolder = holder;
        final String categoria = mListaCategorias.get (position);
        holder.tvTituloLayoutCategoria.setText (categoria);
    }

    @Override
    public int getItemCount () {
        return mListaCategorias.size ();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTituloLayoutCategoria;

        public ViewHolder (View view) {
            super (view);
            view.setOnClickListener (this);
            tvTituloLayoutCategoria = (TextView)view.findViewById (R.id.tvTituloLayoutCategorias);
        }

        @Override
        public void onClick (View v) {

            Intent intent = new Intent (GlobalReceiver.EVENT_RECEIVER);
            intent.putExtra (GlobalReceiver.OPERATION,GlobalReceiver.OPERATION_SELECCION_CATEGORY);
            intent.putExtra (GlobalReceiver.OBJETO_EXTRA_NAME_APP,tvTituloLayoutCategoria.getText ());
            mContext.sendBroadcast (intent);

        }
    }
}
