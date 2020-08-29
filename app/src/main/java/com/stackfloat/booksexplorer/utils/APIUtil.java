package com.stackfloat.booksexplorer.utils;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
            Log.e(TAG, "****************  buildURL failed: *******************   " + e.getMessage());
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
                    publishResultToUI(result, callBacks);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: " + e.getMessage());
                }
            }
        });
    }

    private static void publishResultToUI(final String result, final APIUtlCallBacks callBacks) {
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

    public static ArrayList<String[]> parseJSON(String JSONResult) {
        ArrayList<String[]> books = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JSONResult);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title;
            String Authors;
            String previewLink;
            String dateOfPublication;


            while (i < itemsArray.length()) {

                JSONObject jsonBook = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = jsonBook.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    Authors = volumeInfo.getString("authors");
                    dateOfPublication = volumeInfo.getString("publishedDate");
                    previewLink = volumeInfo.getString("previewLink");
                    String[] book = {title, Authors, dateOfPublication, previewLink};
                    books.add(book);
                } catch (Exception err) {
//                    mTitleText.get().setText(R.string.no_result);
//                    mAuthorText.get().setText("");
                    Log.e("\n" + APIUtil.TAG, "\n" + err.getMessage() + "********************************\n");
                    err.printStackTrace();
                }
                i++;
            }
        } catch (JSONException j_err) {
            j_err.printStackTrace();
        }
        for (String[] book :books) {
            Log.i(TAG, "********************parseJSON:*****************\n\n " + Arrays.toString(book));
        }
        return books;
    }
}
