package com.example.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.data.Project;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private ArrayList<Project> projectList;

    public ProjectAdapter() {
        this.projectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.project_list_item, parent, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project projectName = projectList.get(position);
        holder.bind(projectName.name);
    }

    public void updateProjectList(Project project) {
        this.projectList.add(project);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private TextView projectNameTV;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            this.projectNameTV = itemView.findViewById(R.id.tv_project_name);
        }

        void bind(String name) {
            this.projectNameTV.setText(name);
        }

    }

}
