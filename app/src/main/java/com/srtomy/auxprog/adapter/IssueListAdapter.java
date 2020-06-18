package com.srtomy.auxprog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueViewHolder> implements Filterable {
    private Context context;
    private static RecyclerViewClickListener itemListener;
    //private IssueActivity issueActivity;

    class IssueViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitulo;
        private final FlexboxLayout boxCategorias;
        private final LinearLayout layout;

        private IssueViewHolder(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTituloItemIssue);
            boxCategorias = itemView.findViewById(R.id.box_categoria_item);
            layout = itemView.findViewById(R.id.holder_issue);

/*
            itemView.setOnLongClickListener(evt -> {

                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    selectedPos = position;
                    notifyItemChanged(selectedPos);
                }

                if(issueActivity.deleteItem.isVisible()){
                    issueActivity.deleteItem.setVisible(false);
                    selectedPos = -1;
                }else {
                    issueActivity.deleteItem.setVisible(true);
                }

                return true;
            });

 */

            itemView.setOnClickListener(evt->{
                int position = getAdapterPosition();
                itemListener.recyclerViewListClicked(itemView,position);
            });


        }


    //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
    private final MenuItem.OnMenuItemClickListener onEditMenu = item -> {

        switch (item.getItemId()) {
            case 1:
                //Do stuff
                break;

            case 2:
                //Do stuff

                break;
        }
        return true;
    };
}


    private List<Issue> listIssues; // Cached copy of anotation
    private List<Issue> listIssuesFull;
    private final LayoutInflater mInflater;
    private int selectedPos = -1;

    public IssueListAdapter(Context context, RecyclerViewClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        itemListener = listener;
    }


    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_issue_item, parent, false);
        return new IssueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {

        if(selectedPos == position){
            holder.layout.setSelected(true);
        }else {
            holder.layout.setSelected(false);
        }

        if (listIssues != null) {
            Issue current = listIssues.get(position);
            holder.txtTitulo.setText(current.getTitulo());

            if(current.getCategorias() != null && !current.getCategorias().isEmpty()) {
                String[] cat = current.getCategorias().split(";");
                for (int i = 0; i < cat.length; i++) {
                    addNewChip(cat[i], holder.boxCategorias);
                }
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.txtTitulo.setText("nenhum registro");
        }
    }

    public void setIssues(List<Issue> issues) {
        this.listIssues = issues;
        this.listIssuesFull = new ArrayList<>(issues);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (listIssues != null)
            return listIssues.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return issueFilter;
    }

    private Filter issueFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Issue> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
                filteredList.addAll(listIssuesFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Issue issue : listIssuesFull) {
                    if (issue.getTitulo().toLowerCase().contains(filterPattern) | issue.getDescricao().toLowerCase().contains(filterPattern)){
                        filteredList.add(issue);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listIssues.clear();
            listIssues.addAll((Collection<? extends Issue>) results.values);
            notifyDataSetChanged();
        }
    };

    public int getSelectedPos(){
        return selectedPos;
    }

    private void addNewChip(String person, FlexboxLayout chipGroup) {
        Chip chip = new Chip(context, null, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setText(person);
        chip.setChipBackgroundColorResource(R.color.colorPrimary);
        chip.setClickable(false);
        chip.setCheckable(false);
        chip.setTextIsSelectable(false);
        chipGroup.addView(chip, chipGroup.getChildCount() - 1);
        chip.setOnCloseIconClickListener(evt-> chipGroup.removeView(chip));

        FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) chip.getLayoutParams();
        lp.setMargins(0,5,5,5);
    }
}
