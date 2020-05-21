package com.srtomy.auxprog.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.repository.IssueRepository;

import java.util.List;

public class IssueViewModel extends AndroidViewModel {

    private IssueRepository repo;
    private LiveData<List<Issue>> issues;

    public IssueViewModel(@NonNull Application application) {
        super(application);

        repo = new IssueRepository(application);
        issues = repo.getIssues();
    }

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public void insert(Issue anotacao) {
        repo.insere(anotacao);
    }

    public Issue get(int index){
        return issues.getValue().get(index);
    }

    public void deletar(Issue anotacao){
        repo.remove(anotacao);
    }

}
