package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistapp.data.ProjectData;

public class ProjectDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PROJECT_DATA = "ProjectDetailActivity.ProjectData";

    private ProjectData project = null;

    private boolean isComplete;
    private CompletedProjectsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);


        this.isComplete = false;
        this.viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CompletedProjectsViewModel.class);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_PROJECT_DATA)) {
            this.project = (ProjectData) intent.getSerializableExtra(EXTRA_PROJECT_DATA);

            TextView nameTV = findViewById(R.id.tv_project_detail);
            nameTV.setText(project.getName());
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(project.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project_detail, menu);

        this.viewModel.getCompletedProjectByName(this.project.getName()).observe(
                this,
                new Observer<ProjectData>() {
                    @Override
                    public void onChanged(ProjectData projects) {
                        MenuItem menuItem = menu.findItem(R.id.action_complete);
                        if (projects == null) {
                            isComplete = false;
                            menuItem.setIcon(R.drawable.ic_action_incomplete);
                        } else {
                            isComplete = true;
                            menuItem.setIcon(R.drawable.ic_action_completed);
                        }
                    }
                }
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            case R.id.action_complete:
                toggleComplete(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleComplete(MenuItem item) {
        if (this.project != null) {
            this.isComplete = !this.isComplete;
            item.setChecked(this.isComplete);
            if (this.isComplete) {
                item.setIcon(R.drawable.ic_action_completed);
                this.viewModel.insertCompletedProject(this.project);
            } else {
                item.setIcon(R.drawable.ic_action_incomplete);
                this.viewModel.deleteCompletedProject(this.project);
            }
        }
    }

    private void shareRepo() {
        if (this.project != null) {
            String shareText = getString(
                    R.string.share_project_text,
                    this.project.getName()
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }
    }
}
