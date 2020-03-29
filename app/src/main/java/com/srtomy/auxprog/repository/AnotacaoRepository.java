package com.srtomy.auxprog.repository;

import android.app.Application;

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
}
