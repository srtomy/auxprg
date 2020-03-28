package com.srtomy.auxprog.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.srtomy.auxprog.Anotacao;

import java.util.List;

public interface AnotacaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insere(Anotacao anotacao);

    @Query("SELECT * from Anotacao ORDER BY titulo ASC")
    public LiveData<List<Anotacao>> findAll();

    @Delete
    public void delete();

    @Query("delete from Anotacao")
    public void deleteAll();

}
