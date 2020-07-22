package com.cleanup.todoc;

import com.cleanup.todoc.model.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

@RunWith(JUnit4.class)
/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
//i'dont change this tests a lot, just reference the tested variables with out geters and seters, because i remove them ;)
public class TaskUnitTest {


    @Test
    public void test_projects() {
        Task task1 = new Task(1, "aaa", 123);
        Task task2 = new Task(2, "zzz", 124);
        Task task3 = new Task(3, "hhh", 125);
        Task task4 = new Task(4, "www", 126);

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {
        Task task1 = new Task(1, "aaa", 123);
        Task task2 = new Task(2, "zzz", 124);
        Task task3 = new Task(3, "hhh", 125);

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
        Task task1 = new Task(1, "aaa", 123);
        Task task2 = new Task(2, "zzz", 124);
        Task task3 = new Task(3, "hhh", 125);

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
        Task task1 = new Task(1, "aaa", 123);
        Task task2 = new Task(2, "zzz", 124);
        Task task3 = new Task(3, "hhh", 125);

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
        Task task1 = new Task(1, "aaa", 123);
        Task task2 = new Task(2, "zzz", 124);
        Task task3 = new Task(3, "hhh", 125);

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