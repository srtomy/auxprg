package com.srtomy.auxprog.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.repository.AnotacaoRepository;

import java.util.List;

public class AnotacaoViewModel extends AndroidViewModel {

    private AnotacaoRepository repo;
    private LiveData<List<Anotacao>> anotacoes;

    public AnotacaoViewModel(@NonNull Application application) {
        super(application);

        repo = new AnotacaoRepository(application);
        anotacoes = repo.getAnotacoes();
    }

    public LiveData<List<Anotacao>> getAllWords() {
        return anotacoes;
    }

    public void insert(Anotacao anotacao) {
        repo.insere(anotacao);
    }

    public Anotacao get(int index){
        return anotacoes.getValue().get(index);
    }

    public void deletar(Anotacao anotacao){
        repo.remove(anotacao);
    }

}
