package com.example.todolistapp.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient httpClient = new OkHttpClient();

    public static String doHttpGet() throws IOException {
        Request req = new Request.Builder().url("https://api.todoist.com/rest/v1/projects").addHeader("Authorization", " Bearer 0169ab8d7ae171d773ae6061360d7204bf998120").build();
//        Request req = new Request.Builder().url("https://api.todoist.com/rest/v1/projects").build();
        Response res = httpClient.newCall(req).execute();

        try {
            return res.body().string();
        }
        finally {
            res.close();
        }
    }
}
