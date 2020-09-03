package com.stackfloat.booksexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.stackfloat.booksexplorer.databinding.ActivityBookDetailBinding;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK = "com.stackfloat.booksexplorer.BookDetailsActivity.book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Book book= getIntent().getParcelableExtra(EXTRA_BOOK);
        ActivityBookDetailBinding detailBinding=
                DataBindingUtil.setContentView(this,R.layout.activity_book_detail);
        detailBinding.setBook(book);
    }
}