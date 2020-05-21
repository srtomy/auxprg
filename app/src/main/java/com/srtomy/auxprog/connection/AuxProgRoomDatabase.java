package com.srtomy.auxprog.connection;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.converter.LocalDateTimeConverter;
import com.srtomy.auxprog.dao.AnotacaoDao;
import com.srtomy.auxprog.dao.IssueDao;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */

@Database(entities = {Anotacao.class, Issue.class}, version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter.class)
public abstract class AuxProgRoomDatabase extends RoomDatabase {

    public abstract AnotacaoDao anotacaoDao();
    public abstract IssueDao issueDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AuxProgRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

   public static AuxProgRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AuxProgRoomDatabase.class) {
                if (INSTANCE == null) {
                    //context.deleteDatabase("auxprog_database");

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AuxProgRoomDatabase.class, "auxprog_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                //AnotacaoDao dao = INSTANCE.anotacaoDao();
                //dao.deleteAll();
                IssueDao issueDao =INSTANCE.issueDao();
                Issue issue = new Issue();
                issue.setTitulo("Teste");
                issue.setSolucao("Teste");
                issue.setDescricao("TEste");
                //issueDao.insere(issue);

            });
        }
    };
}
