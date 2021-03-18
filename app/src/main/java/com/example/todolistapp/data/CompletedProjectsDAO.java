package com.example.todolistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompletedProjectsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProjectData project);

    @Delete
    void delete(ProjectData project);

    @Query("SELECT * FROM completedProjects ORDER BY timestamp DESC")
    LiveData<List<ProjectData>> getAllCompletedProjects();

    @Query("SELECT * FROM completedProjects WHERE name = :name LIMIT 1")
    LiveData<ProjectData> getCompletedProjectByName(String name);
}
