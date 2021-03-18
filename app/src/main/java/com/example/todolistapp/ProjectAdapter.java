package com.example.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.data.Project;
import com.example.todolistapp.data.ProjectData;
import com.example.todolistapp.data.Results;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private Results results;
    private OnProjectItemClickListener onProjectItemClickListener;

    public interface OnProjectItemClickListener {
        void onProjectItemClick(ProjectData projectData);
    }

    public ProjectAdapter(OnProjectItemClickListener onProjectItemClickListener) {
        this.onProjectItemClickListener = onProjectItemClickListener;
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
        ProjectData project = this.results.getProjectDataList().get(position);
        holder.bind(project);
    }

    public void updateProjectList(Results results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void addToProjectList(List<ProjectData> projects) {
        this.results = new Results();
        this.results.getProjectDataList().addAll(projects);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return this.results.getProjectDataList().size();
        } else {
            return 0;
        }
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private TextView projectNameTV;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            this.projectNameTV = itemView.findViewById(R.id.tv_project_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProjectItemClickListener.onProjectItemClick(
                            results.getProjectDataList().get(getAdapterPosition())
                    );
                }
            });
        }

        public void bind(ProjectData projectData) {
            this.projectNameTV.setText(projectData.getName());
        }

    }

}
