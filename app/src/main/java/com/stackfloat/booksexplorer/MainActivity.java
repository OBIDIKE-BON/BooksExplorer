package com.stackfloat.booksexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityViewModel mMainActivityViewModel;
    private BooksRecyclerAdapter mBooksRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        err = findViewById(R.id.txt_error);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mMainActivityViewModel = viewModelProvider.get(MainActivityViewModel.class);
        mBooksRecyclerAdapter = new BooksRecyclerAdapter(this);
        mRecyclerView = findViewById(R.id.rv_search_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mBooksRecyclerAdapter);
        mMainActivityViewModel.mBooks.observe(this, new Observer<ArrayList<Book>>() {
            @Override
            public void onChanged(ArrayList<Book> books) {
                Log.d(TAG, "onChanged: ");
                if (books.size() != 0) {
                    mBooksRecyclerAdapter.setBookList(books);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    err.setVisibility(View.INVISIBLE);
                }else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    err.setText("No Results Found, Please Search Again!");
                    err.setVisibility(View.VISIBLE);
                }
            }
        });
        loadBook("google");
    }

    private void loadBook(String queryString) {
        try {
            URL url = mMainActivityViewModel.buildURL(queryString);
            if (url != null) {
                mProgressBar.setVisibility(View.VISIBLE);
                mMainActivityViewModel.query(url);
            }
        } catch (Exception e) {
            mProgressBar.setVisibility(View.INVISIBLE);
            err.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.books_search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()/*new ComponentName(this,SearchActivity.class)*/));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                if ((queryString.length()!=0)) {
                    loadBook(queryString);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String queryString) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}