package com.stackfloat.booksexplorer;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executor;

import javax.net.ssl.HttpsURLConnection;

public class APIUtil {

    private static final String API_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String TAG = APIUtil.class.getSimpleName();
    private static final String QUERY_PARAMETER_KEY = "q";
    private static final String API_KEY = "AIzaSyDLc6RgHOHecWktJnrNLRqFrDXQ4y2fZn4";
    private static final String KEY = "key";

    private APIUtil() {
    }

    public static URL buildURL(String queryString) {
        Uri uri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, queryString)
                .appendQueryParameter(KEY, API_KEY)
                .build();

        Log.d(TAG, "buildURL: " + uri.toString());
        try {
            return new URL(uri.toString());
        } catch (IOException e) {
            Log.e(TAG, "****************  buildURL: *******************   " + e.getMessage());
            return null;
        }
    }

    public static void query(final URL url, Executor executor, final APIUtlCallBacks callBacks) {
        Log.d(TAG, "query: ");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + Thread.currentThread().getId());
                try {
                    String result = asyncQuery(url);
                    sendResult(result, callBacks);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: " + e.getMessage());
                }
            }
        });
    }

    private static void sendResult(final String result, final APIUtlCallBacks callBacks) {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBacks.onQueryResult(result);
            }
        });
    }

    public static String asyncQuery(URL url) throws IOException {
        Log.d(TAG, "asyncQuery: ");
        String result = "";
        InputStream stream = null;
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        httpsURLConnection.setRequestProperty("Accept", "application/json");
        try {
             stream = httpsURLConnection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                scanner.useDelimiter("\\A");
                result = scanner.next();
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "query: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (stream != null) {
                stream.close();
                httpsURLConnection.disconnect();
            }
        }
    }
}
