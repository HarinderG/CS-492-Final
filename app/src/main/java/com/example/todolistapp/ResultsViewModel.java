package com.example.todolistapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistapp.data.Results;
import com.example.todolistapp.data.ResultsRepository;

public class ResultsViewModel extends ViewModel {
    private ResultsRepository repository;
    private LiveData<Results> results;

    public ResultsViewModel() {
        this.repository = new ResultsRepository();
        this.results = repository.getResults();
    }

    public LiveData<Results> getResults() {
        return this.results;
    }

    public void loadProjects() {
        this.repository.loadProjects();
    }
}
