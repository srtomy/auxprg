package com.srtomy.auxprog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnotacaoListAdapter extends RecyclerView.Adapter<AnotacaoListAdapter.AnotacaoViewHolder> implements Filterable {
    private Context context;
    private static RecyclerViewClickListener itemListener;
    public int selectedPos = -1;


    class AnotacaoViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final TextView txtCategoria;
        private final LinearLayout boxIten;

        private AnotacaoViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            boxIten = itemView.findViewById(R.id.boxIten);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);

            itemView.setLongClickable(true);

            itemView.setOnClickListener(evt -> {
                itemListener.recyclerViewListClicked(itemView, this.getLayoutPosition());
            });

            itemView.setOnLongClickListener(v -> {
                // Triggers click upwards to the adapter on click
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
                    notifyItemChanged(selectedPos);
                    itemListener.recyclerViewListLongClicked(v,selectedPos);
                    return true;
                }
                return false;
            });
        }

        public void changeToSelect(int colorBackground) {
            wordItemView.setBackgroundColor(colorBackground);
        }
    }


    private final LayoutInflater mInflater;
    private List<Anotacao> mAnotacoes; // Cached copy of anotation
    private List<Anotacao> anotacaoListFull;

    public AnotacaoListAdapter(Context context, RecyclerViewClickListener itemListener) {
        mInflater = LayoutInflater.from(context);
        this.itemListener = itemListener;
    }

    @Override
    public AnotacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AnotacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnotacaoViewHolder holder, int position) {
        if (mAnotacoes != null) {

            if(selectedPos == position){
               holder.boxIten.setSelected(true);
            }else {
                holder.boxIten.setSelected(false);
            }

            Anotacao current = mAnotacoes.get(position);
            holder.wordItemView.setText(current.getTitulo());
            holder.txtCategoria.setText(current.getCategoria());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.mAnotacoes = anotacoes;
        this.anotacaoListFull = new ArrayList<>(anotacoes);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mAnotacoes != null)
            return mAnotacoes.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return anotacaoFilter;
    }

    private Filter anotacaoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Anotacao> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
                filteredList.addAll(anotacaoListFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Anotacao anotacao : anotacaoListFull) {
                    if (anotacao.getTitulo().toLowerCase().contains(filterPattern) | anotacao.getCategoria().toLowerCase().contains(filterPattern)){
                        filteredList.add(anotacao);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAnotacoes.clear();
            mAnotacoes.addAll((Collection<? extends Anotacao>) results.values);
            notifyDataSetChanged();
        }
    };
}
