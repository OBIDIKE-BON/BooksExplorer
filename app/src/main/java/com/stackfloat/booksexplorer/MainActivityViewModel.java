package com.stackfloat.booksexplorer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.stackfloat.booksexplorer.utils.APIUtil;
import com.stackfloat.booksexplorer.utils.APIUtlCallBacks;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    public MutableLiveData<String> JSONResponseMutableLiveData = new MutableLiveData<>();
    private static final Executor mExecutor = new Executor() {
        @Override
        public void execute(Runnable runnable) {
            Log.d(TAG, "execute: ");
            new Thread(runnable).start();
        }
    };
    public MutableLiveData<ArrayList<String[]>> mBooks= new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "MainActivityViewModel: ");
    }

    public URL buildURL(String queryString) {
        Log.d(TAG, "buildURL: ");
        return APIUtil.buildURL(queryString);
    }

    public void query(final URL url) {
        APIUtil.query(url, mExecutor, new APIUtlCallBacks() {
            @Override
            public void onQueryResult(String JSONResponse) {
                Log.d(TAG, "onQueryResult: ");
                JSONResponseMutableLiveData.setValue(JSONResponse);
                mBooks .setValue(getBooks(JSONResponse));

            }
        });
    }
    public ArrayList<String[]> getBooks(String JSONResponse){
        return APIUtil.parseJSON(JSONResponse);
    }
}
