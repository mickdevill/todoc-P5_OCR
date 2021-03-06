package com.cleanup.todoc.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract TaskDAO taskDAO();

    //very important thing used to saw app in threads, it's used in repository
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
