package com.example.todolistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CompletedProjectsRepository {
    private CompletedProjectsDAO dao;

    public CompletedProjectsRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.completedProjectsDAO();
    }

    public void insertCompletedProject(ProjectData project) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(project);
            }
        });
    }

    public void deleteCompletedProject(ProjectData project) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(project);
            }
        });
    }

    public LiveData<List<ProjectData>> getAllCompletedProjects() {
        return this.dao.getAllCompletedProjects();
    }

    public LiveData<ProjectData> getCompletedProjectByName(String name) {
        return this.dao.getCompletedProjectByName(name);
    }
}
