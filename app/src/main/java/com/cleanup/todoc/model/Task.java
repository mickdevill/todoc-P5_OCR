package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

//it's the entity class used by room
@Entity(tableName = "tasks")
public class Task {
    //all variables are public, and I remove the geters and seters to let room get them(variables) directly
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "projectID")
    public long projectId;

    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "creationTimestamp")
    public long creationTimestamp;

    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    //this is the only geter i don't touch because the TaskAdapter required it, to get instance of project from list of project by id
    @Nullable
    public Project getProject() {
        return Project.getProjectById(projectId);
    }


    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
