package com.srtomy.auxprog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.adapter.IssueListAdapter;
import com.srtomy.auxprog.model.IssueViewModel;

public class IssueActivity extends AppCompatActivity {

    private IssueViewModel issueViewModel;
    private IssueListAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton btnCadastrar;
    public MenuItem deleteItem;

    public static final int NEW_ISSUE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ISSUE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_issue);

        // Get a new or existing ViewModel from the ViewModelProvider.
        issueViewModel = new ViewModelProvider(this).get(IssueViewModel.class);
        adapter = new IssueListAdapter(this);

        initLayout();

        initActions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.issue_menu, menu);

        deleteItem = menu.findItem(R.id.btn_deletar_issue);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_deletar_issue:
                deletar(issueViewModel.get(adapter.getSelectedPos()));
                deleteItem.setVisible(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initLayout(){
        btnCadastrar = findViewById(R.id.btnSalvarIssue);
        recyclerView = findViewById(R.id.recyclerview_issue);

        registerForContextMenu(recyclerView);

        //divider
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));

        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            Intent intent = new Intent(this, IssueDetailsActivity.class);
            intent.putExtra("issue", new Issue());
            startActivityForResult(intent, NEW_ISSUE_ACTIVITY_REQUEST_CODE);
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Issue issue = (Issue) data.getSerializableExtra("issue");
            issueViewModel.insert(issue);
        } else if (requestCode == UPDATE_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Issue issue = (Issue) data.getSerializableExtra("issue");
            issueViewModel.insert(issue);
        }
    }

    private void deletar(Issue issue){
        if(issue != null)
            issueViewModel.deletar(issue);
    }

   public void showDetails(int position){
       Intent intent = new Intent(this, IssueDetailsActivity.class);
       intent.putExtra("issue", issueViewModel.get(position));
       startActivityForResult(intent, UPDATE_ISSUE_ACTIVITY_REQUEST_CODE);

   }
}
