package com.example.ws01;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAsync extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0].toString();
        String result = HttpConnect.getString(url);
        Log.d("[WebData]",result);
        return result;
    }
}
