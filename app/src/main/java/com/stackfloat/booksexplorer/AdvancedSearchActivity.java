package com.stackfloat.booksexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stackfloat.booksexplorer.databinding.ActivityAdvancedSearchBinding;
import com.stackfloat.booksexplorer.utils.APIUtil;

import java.net.URL;

public class AdvancedSearchActivity extends AppCompatActivity {

    private ActivityAdvancedSearchBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_advanced_search);

    }

    public void searchBooks(View view) {
        String title = mBinding.etTitle.getText().toString().trim();
        String author = mBinding.etAuthor.getText().toString().trim();
        String publisher = mBinding.etPublisher.getText().toString().trim();
        String isbn = mBinding.etIsbn.getText().toString().trim();
        if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()) {
            Toast.makeText(this, R.string.no_text_error, Toast.LENGTH_LONG).show();
        } else {
            int position = SharedPrefs.getIntPref(this, SharedPrefs.POSITION);
            if (position == 0 || position == 5) {
                position = 1;
            } else {
                position++;
            }
            String key = SharedPrefs.QUERY + position;
            String value = title + "," + author + "," + publisher + "," + isbn;
            SharedPrefs.setStringPref(this, key, value);
            SharedPrefs.setIntPref(this, SharedPrefs.POSITION, position);
            String url = APIUtil.buildSearchURL(title, author, publisher, isbn);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_QUERY, url);
            startActivity(intent);
        }
    }
}