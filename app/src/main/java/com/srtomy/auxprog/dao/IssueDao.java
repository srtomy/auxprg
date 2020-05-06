package com.srtomy.auxprog.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.srtomy.auxprog.Issue;

import java.util.List;

@Dao
public interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insere(Issue issue);

    @Query("SELECT * from Issue ORDER BY titulo ASC")
    public LiveData<List<Issue>> findAll();

    @Delete
    public void delete(Issue issue);

    @Query("delete from Issue")
    public void deleteAll();

}
