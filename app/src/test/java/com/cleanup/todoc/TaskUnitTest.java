package com.cleanup.todoc;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.cleanup.todoc.DB.DB;
import com.cleanup.todoc.DB.Repository;
import com.cleanup.todoc.DB.TaskDAO;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainViewModel;

import org.junit.BeforeClass;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

@Test
    public void test_projects() {
        Task task1 = new Task();
        task1.projectId = 1;
        task1.name = "aaa";
        task1.creationTimestamp = 123;

        Task task2 = new Task();
        task2.projectId = 2;
        task2.name = "zzz";
        task2.creationTimestamp = 124;

        Task task3 = new Task();
        task3.projectId = 3;
        task3.name = "hhh";
        task3.creationTimestamp = 125;

        Task task4 = new Task();
        task4.projectId = 4;
        task4.name = "WRRRRYYYYYYYYY";
        task4.creationTimestamp = 126;

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {

        Task task1 = new Task();
        task1.projectId = 1;
        task1.name = "aaa";
        task1.creationTimestamp = 123;

        Task task2 = new Task();
        task2.projectId = 2;
        task2.name = "zzz";
        task2.creationTimestamp = 124;

        Task task3 = new Task();
        task3.projectId = 3;
        task3.name = "hhh";
        task3.creationTimestamp = 125;

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        Task task1 = new Task();
        task1.projectId = 1;
        task1.name = "aaa";
        task1.creationTimestamp = 123;

        Task task2 = new Task();
        task2.projectId = 2;
        task2.name = "zzz";
        task2.creationTimestamp = 124;

        Task task3 = new Task();
        task3.projectId = 3;
        task3.name = "hhh";
        task3.creationTimestamp = 125;

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        Task task1 = new Task();
        task1.projectId = 1;
        task1.name = "aaa";
        task1.creationTimestamp = 123;

        Task task2 = new Task();
        task2.projectId = 2;
        task2.name = "zzz";
        task2.creationTimestamp = 124;

        Task task3 = new Task();
        task3.projectId = 3;
        task3.name = "hhh";
        task3.creationTimestamp = 125;

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        Task task1 = new Task();
        task1.projectId = 1;
        task1.name = "aaa";
        task1.creationTimestamp = 123;

        Task task2 = new Task();
        task2.projectId = 2;
        task2.name = "zzz";
        task2.creationTimestamp = 124;

        Task task3 = new Task();
        task3.projectId = 3;
        task3.name = "hhh";
        task3.creationTimestamp = 125;

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }


}