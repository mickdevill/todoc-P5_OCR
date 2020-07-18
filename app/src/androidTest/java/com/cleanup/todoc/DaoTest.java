package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.DB.DB;
import com.cleanup.todoc.DB.TaskDAO;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    private TaskDAO Dao;
    private DB db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, DB.class).build();
        Dao = db.taskDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }


    @Test
    public void getTheliveDataWithOutTasks() throws Exception {

        List<Task> tasks = dbTestUtil.getValue(this.db.taskDAO().getLiveData());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertionTest() throws Exception {
        Task task = new Task();
        task.projectId = 2;
        task.name = "zzz";
        task.creationTimestamp = 124;

        this.db.taskDAO().insert(task);

        List<Task> tasks = dbTestUtil.getValue(this.db.taskDAO().getLiveData());
        assertTrue(tasks.size() == 1);

    }

    @Test
    public void removeTasks() throws Exception {
        Task task = new Task();
        task.projectId = 2;
        task.name = "zzz";
        task.creationTimestamp = 124;

        this.db.taskDAO().insert(task);
        this.db.taskDAO().delete(task);

        List<Task> tasks = dbTestUtil.getValue(this.db.taskDAO().getLiveData());
        assertFalse(tasks.contains(task));

    }


}
