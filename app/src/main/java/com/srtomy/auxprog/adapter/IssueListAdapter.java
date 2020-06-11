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


import androidx.recyclerview.widget.RecyclerView;

import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.IssueActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueViewHolder> implements Filterable {
    private Context context;
    //private IssueActivity issueActivity;

    class IssueViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitulo;
        private final TextView txtDescricao;
        private final LinearLayout layout;

        private IssueViewHolder(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTituloItemIssue);
            txtDescricao = itemView.findViewById(R.id.txtDescItenIssue);
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

            itemView.setOnClickListener(evt->{
                int position = getAdapterPosition();
                issueActivity.showDetails(position);
            });

                 */
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

    public IssueListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        //this.issueActivity = (IssueActivity) context;
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
            holder.txtDescricao.setText(current.getDescricao());
        } else {
            // Covers the case of data not being ready yet.
            holder.txtTitulo.setText("nenhum registro");
            holder.txtDescricao.setText("");
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
}
