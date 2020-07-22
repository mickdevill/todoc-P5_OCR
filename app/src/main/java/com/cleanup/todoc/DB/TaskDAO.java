package com.cleanup.todoc.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    //query to get live data list of DB
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getLiveData();

    //insert new tast
    @Insert
    void insert(Task task);

    //remove task
    @Delete
    void delete(Task task);


}
