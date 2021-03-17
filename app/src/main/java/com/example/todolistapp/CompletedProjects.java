package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.data.ProjectData;

import java.util.List;

public class CompletedProjects extends AppCompatActivity implements ProjectAdapter.OnProjectItemClickListener {
    private static final String TAG = CompletedProjects.class.getSimpleName();

    private CompletedProjectsViewModel viewModel;

    private TextView errorMessageTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_projects);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Completed Projects");

        this.errorMessageTV = findViewById(R.id.tv_error_message);

        RecyclerView completedProjectsRV = findViewById(R.id.rv_completed_projects);
        completedProjectsRV.setLayoutManager(new LinearLayoutManager(this));
        completedProjectsRV.setHasFixedSize(true);

        ProjectAdapter adapter = new ProjectAdapter(this);
        completedProjectsRV.setAdapter(adapter);

        this.viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CompletedProjectsViewModel.class);

        this.viewModel.getAllCompletedProjects().observe(
                this,
                new Observer<List<ProjectData>>() {
                    @Override
                    public void onChanged(List<ProjectData> projectData) {
                        adapter.addToProjectList(projectData);
                    }
                }
        );

        if (adapter.getItemCount() == 0) {
            Log.d(TAG, "No completed projects");
            this.errorMessageTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProjectItemClick(ProjectData projectData) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra(ProjectDetailActivity.EXTRA_PROJECT_DATA, projectData);
        startActivity(intent);
    }
}
