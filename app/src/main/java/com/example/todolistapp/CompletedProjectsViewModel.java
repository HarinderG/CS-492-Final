package com.example.todolistapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolistapp.data.CompletedProjectsRepository;
import com.example.todolistapp.data.ProjectData;

import java.util.List;

public class CompletedProjectsViewModel extends AndroidViewModel {
    private CompletedProjectsRepository repository;

    public CompletedProjectsViewModel(Application application) {
         super(application);
         this.repository = new CompletedProjectsRepository(application);
    }

    public void insertCompletedProject(ProjectData project) {
        this.repository.insertCompletedProject(project);
    }

    public void deleteCompletedProject(ProjectData project) {
        this.repository.deleteCompletedProject(project);
    }

    public LiveData<List<ProjectData>> getAllCompletedProjects() {
        return this.repository.getAllCompletedProjects();
    }

    public LiveData<ProjectData> getCompletedProjectByName(String name) {
        return this.repository.getCompletedProjectByName(name);
    }
}
