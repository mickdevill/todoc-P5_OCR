package com.cleanup.todoc.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//the project class, that class can't be entity, because if i make it entity it will be monkey business, this class contain all what i need
//to make DB integration
public class Project {

    private final long id;

    @NonNull
    private final String name;

    @ColorInt
    private final int color;

    private Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    //the get all project used to assign project to task with id
    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

    //its used in recyclerview, and called from task class
    @Nullable
    public static Project getProjectById(long id) {
        for (Project project : getAllProjects()) {
            if (project.id == id)
                return project;
        }
        return null;
    }

    public long getId() {
        return id;
    }


    @NonNull
    public String getName() {
        return name;
    }


    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
