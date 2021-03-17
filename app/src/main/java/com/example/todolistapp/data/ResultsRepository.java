package com.example.todolistapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ResultsRepository {
    private static final String BASE_URL = "https://api.todoist.com/rest/v1/";

    private MutableLiveData<Results> results;

    public ResultsRepository() {
        this.results = new MutableLiveData<>();
        this.results.setValue(null);
    }

    public LiveData<Results> getResults() {
        return this.results;
    }

    public void loadProjects() {
        this.results.setValue(null);
        Results fakeResults = new Results("fakeProject");
        this.results.setValue(fakeResults);
    }
}
