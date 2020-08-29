package com.stackfloat.booksexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityViewModel mMainActivityViewModel;
    private String mJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mMainActivityViewModel = viewModelProvider.get(MainActivityViewModel.class);
        final TextView response = findViewById(R.id.tv_response);
        mJSON = "null";
        try {
            URL url = mMainActivityViewModel.buildURL("cooking");
            if (url != null) {
                mMainActivityViewModel.query(url);
                mMainActivityViewModel.JSONResponseMutableLiveData.observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.d(TAG, "onChanged: ");
                        mJSON = s;
                        response.setText(mJSON);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}