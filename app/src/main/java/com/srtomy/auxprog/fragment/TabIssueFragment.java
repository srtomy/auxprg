package com.srtomy.auxprog.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.IssueDetailsActivity;
import com.srtomy.auxprog.adapter.IssueListAdapter;
import com.srtomy.auxprog.model.IssueViewModel;

public class TabIssueFragment extends Fragment {
    private IssueViewModel issueViewModel;
    private IssueListAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton btnCadastrar;
    public MenuItem deleteItem;

    private View view;
    private Context context;

    public static final int NEW_ISSUE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ISSUE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_issue, container, false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new IssueListAdapter(view.getContext());

        // Get a new or existing ViewModel from the ViewModelProvider.
        issueViewModel = new ViewModelProvider(this).get(IssueViewModel.class);
        adapter = new IssueListAdapter(context);

        initLayout();

        initActions();

        return view;
    }

    public void initLayout(){
        btnCadastrar = view.findViewById(R.id.btnSalvarIssue);
        recyclerView = view.findViewById(R.id.recyclerview_issue);

        registerForContextMenu(recyclerView);

        //divider
        //DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //divider.setDrawable(ContextCompat.getDrawable(context.getBaseContext(), R.drawable.line_divider));

        //recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public void initActions() {
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        issueViewModel.getIssues().observe(this, issues -> {
            // Update the cached copy of the words in the adapter.
            adapter.setIssues(issues);
        });

        btnCadastrar.setOnClickListener(evt -> {
            Intent intent = new Intent(context, IssueDetailsActivity.class);
            intent.putExtra("issue", new Issue());
            startActivityForResult(intent, NEW_ISSUE_ACTIVITY_REQUEST_CODE);
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Issue issue = (Issue) data.getSerializableExtra("issue");
            issueViewModel.insert(issue);
        } else if (requestCode == UPDATE_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Issue issue = (Issue) data.getSerializableExtra("issue");
            issueViewModel.insert(issue);
        }
    }

    private void deletar(Issue issue){
        if(issue != null)
            issueViewModel.deletar(issue);
    }

    public void showDetails(int position){
        Intent intent = new Intent(context, IssueDetailsActivity.class);
        intent.putExtra("issue", issueViewModel.get(position));
        startActivityForResult(intent, UPDATE_ISSUE_ACTIVITY_REQUEST_CODE);

    }


}