package com.cleanup.todoc;

import android.app.Application;

import androidx.room.Room;

import com.cleanup.todoc.DB.DB;
import com.cleanup.todoc.DB.TaskDAO;

public class AppST extends Application {

    private DB database;
    private TaskDAO taskDAO;

    private static  AppST instance;


    @Override
    public void onCreate() {
        super.onCreate();
instance = this;


        database = Room.databaseBuilder(getApplicationContext(),
                DB.class, "db").build();

        taskDAO = database.taskDAO();
    }

    public DB getDatabase() {
        return database;
    }

    public void setDatabase(DB database) {
        this.database = database;
    }

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public static AppST getInstance() {
        return instance;
    }
}
