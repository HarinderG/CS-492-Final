package com.example.todolistapp.utils;

import com.example.todolistapp.data.Project;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoistUtils {
    public static ArrayList<Project> parseProjects(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Project>>(){}.getType();
        ArrayList<Project> results = gson.fromJson(json, listType);

        return results != null ? results : null;
    }
}
