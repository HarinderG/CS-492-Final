package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolistapp.data.Project;
import com.example.todolistapp.utils.NetworkUtils;
import com.example.todolistapp.utils.TodoistUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText searchBox;
    private RecyclerView projectListRV;
    private ProjectAdapter projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.searchBox = findViewById(R.id.et_search_box);
        Button search_button = findViewById(R.id.btn_search_button);

//        Project List RV setup
        this.projectListRV = findViewById(R.id.rv_project_list);
        this.projectAdapter = new ProjectAdapter();
        this.projectListRV.setAdapter(this.projectAdapter);
        this.projectListRV.setLayoutManager(new LinearLayoutManager(this));
        this.projectListRV.setHasFixedSize(true);
        this.projectListRV.setItemAnimator(new DefaultItemAnimator());

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProjectSearchTask().execute("url");
            }
        });
    }

    public class ProjectSearchTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String results = null;
            try {
                results = NetworkUtils.doHttpGet();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String res) {
            if (res != null) {
                ArrayList<Project> projectList = TodoistUtils.parseProjects(res);
                for (int i = 0; i < projectList.size(); i++) {
                    projectAdapter.updateProjectList(projectList.get(i));
                }
                Log.d(TAG, "=== " + projectList.get(0).name);
            }
        }
    }
}