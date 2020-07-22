package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.DB.Repository;
import com.cleanup.todoc.model.Task;

import java.util.List;
//the MainViewModel class apply repository methods referenced to DAO querys already sawed in threads by the ExecutorService in DB class

public class MainViewModel extends androidx.lifecycle.ViewModel {

    private Repository repository;

    private LiveData<List<Task>> LVD;


    public MainViewModel() {
        repository = new Repository();
        LVD = repository.getLVD();

    }

    public LiveData<List<Task>> getLVD() {
        return LVD;
    }


    public void insert(Task task) {
        repository.insert(task);
    }

    public void delete(Task task) {
        repository.delete(task);
    }
}
