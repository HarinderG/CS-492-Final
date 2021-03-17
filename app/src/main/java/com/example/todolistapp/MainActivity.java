package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todolistapp.data.Project;
import com.example.todolistapp.utils.NetworkUtils;
import com.example.todolistapp.utils.TodoistUtils;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText searchBox;
    private RecyclerView projectListRV;
    private ProjectAdapter projectAdapter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.searchBox = findViewById(R.id.et_search_box);
        Button search_button = findViewById(R.id.btn_search_button);

//        Project List RV setup
        this.projectListRV = findViewById(R.id.rv_project_list);
        this.projectAdapter = new ProjectAdapter();
        this.projectListRV.setAdapter(this.projectAdapter);
        this.projectListRV.setLayoutManager(new LinearLayoutManager(this));
        this.projectListRV.setHasFixedSize(true);
        this.projectListRV.setItemAnimator(new DefaultItemAnimator());

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProjectSearchTask().execute("url");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.nav_completed_projects:
                Log.d(TAG, "Completed Projects Activity");
//                Intent completedProjectsIntent = new Intent(this, CompletedProject.class);
//                startActivity(completedProjectsIntent);
                return true;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.nav_camera:
                Log.d(TAG, "Take a new profile photo");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 1);
                } catch (ActivityNotFoundException e){
                    Log.d(TAG, "Error starting camera intent");
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.profile_photo);
            imageView.setImageBitmap(imageBitmap);
        }
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