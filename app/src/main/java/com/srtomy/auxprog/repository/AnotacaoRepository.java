package com.srtomy.auxprog.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.connection.AuxProgRoomDatabase;
import com.srtomy.auxprog.dao.AnotacaoDao;

import java.util.List;

public class AnotacaoRepository {
    private AnotacaoDao dao;
    private LiveData<List<Anotacao>> anotacoes;

    public AnotacaoRepository(Application application) {
        AuxProgRoomDatabase db = AuxProgRoomDatabase.getDatabase(application);
        dao = db.anotacaoDao();
        anotacoes = dao.findAll();
    }

    public LiveData<List<Anotacao>> getAnotacoes(){
        return anotacoes;
    }

    public void insere(Anotacao anotacao){
        AuxProgRoomDatabase.databaseWriteExecutor.execute(()->{
            dao.insere(anotacao);
        });
    }

    public void remove(Anotacao anotacao){
        new deleteWordAsyncTask(dao).execute(anotacao);
    }

    private static class deleteWordAsyncTask extends AsyncTask<Anotacao, Void, Void> {
        private AnotacaoDao mAsyncTaskDao;

        deleteWordAsyncTask(AnotacaoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Anotacao... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
