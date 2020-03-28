package com.srtomy.auxprog.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.roomwordssample.WordRoomDatabase;
import com.example.android.roomwordssample.WordRoomDatabase_Impl;
import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.dao.AnotacaoDao;

import java.util.List;

public class AnotacaoRepository {
    private AnotacaoDao dao;
    private LiveData<List<Anotacao>> anotacoes;

    public AnotacaoRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        dao = db.anotacaoDao();
        anotacoes = dao.findAll();
    }

    private LiveData<List<Anotacao>> getAnotacoes(){
        return anotacoes;
    }

    public void insere(Anotacao anotacao){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            dao.insere(anotacao);
        });
    }
}
