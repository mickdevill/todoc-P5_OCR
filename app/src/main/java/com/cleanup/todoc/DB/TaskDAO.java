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

    @Query("SELECT * FROM zadachi")
    LiveData<List<Task>> getLiveData();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);


}
