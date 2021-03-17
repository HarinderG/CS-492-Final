package com.example.todolistapp.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "completedProjects")
public class ProjectData implements Serializable {

    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    private long timestamp;

    public ProjectData(String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
