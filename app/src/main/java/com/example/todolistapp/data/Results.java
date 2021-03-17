package com.example.todolistapp.data;

import java.util.ArrayList;

public class Results {

    private ArrayList<ProjectData> projectDataList;

    public Results() {
        this.projectDataList = new ArrayList<>();
    }

    public Results(String name) {
        ProjectData fakeProjectData = new ProjectData("fakeProject", System.currentTimeMillis());
        this.projectDataList = new ArrayList<>();
        this.projectDataList.add(fakeProjectData);
    }

    public ArrayList<ProjectData> getProjectDataList() {
        return projectDataList;
    }
}
