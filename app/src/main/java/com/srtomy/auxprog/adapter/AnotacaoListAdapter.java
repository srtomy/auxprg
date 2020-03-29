package com.srtomy.auxprog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.R;

import java.util.List;

public class AnotacaoListAdapter extends RecyclerView.Adapter<AnotacaoListAdapter.AnotacaoViewHolder>{

    class AnotacaoViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private AnotacaoViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Anotacao> mAnotacoes; // Cached copy of anotation

    public AnotacaoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public AnotacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AnotacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnotacaoViewHolder holder, int position) {
        if (mAnotacoes != null) {
            Anotacao current = mAnotacoes.get(position);
            holder.wordItemView.setText(current.getTitulo());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.mAnotacoes = anotacoes;
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
}
