package com.stackfloat.booksexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityViewModel mMainActivityViewModel;
    private String mJSON;
    private BooksRecyclerAdapter mBooksRecyclerAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mMainActivityViewModel = viewModelProvider.get(MainActivityViewModel.class);
        mBooksRecyclerAdapter = new BooksRecyclerAdapter(this);
        mRecyclerView = findViewById(R.id.rv_search_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mBooksRecyclerAdapter);
        mJSON = "null";
        try {
            URL url = mMainActivityViewModel.buildURL("cooking");
            if (url != null) {
                mMainActivityViewModel.query(url);
                mMainActivityViewModel.mBooks.observe(this, new Observer<ArrayList<String[]>>() {
                    @Override
                    public void onChanged(ArrayList<String[]> books) {
                        Log.d(TAG, "onChanged: ");
                        mBooksRecyclerAdapter.setBookList(books);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}