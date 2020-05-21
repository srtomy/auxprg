package com.srtomy.auxprog.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.connection.AuxProgRoomDatabase;
import com.srtomy.auxprog.dao.IssueDao;

import java.util.List;

public class IssueRepository {
    private IssueDao dao;
    private LiveData<List<Issue>> issues;

    public IssueRepository(Application application) {
        AuxProgRoomDatabase db = AuxProgRoomDatabase.getDatabase(application);
        dao = db.issueDao();
        issues = dao.findAll();
    }

    public LiveData<List<Issue>> getIssues(){
        return issues;
    }

    public void insere(Issue issue){
        AuxProgRoomDatabase.databaseWriteExecutor.execute(()->{
            dao.insere(issue);
        });
    }

    public void remove(Issue issue){
        new deleteAsyncTask(dao).execute(issue);
    }

    private static class deleteAsyncTask extends AsyncTask<Issue, Void, Void> {
        private IssueDao mAsyncTaskDao;

        deleteAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Issue... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
