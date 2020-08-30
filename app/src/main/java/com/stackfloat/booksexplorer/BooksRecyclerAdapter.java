package com.stackfloat.booksexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder> {

    public final Context mContext;
    private ArrayList<String[]> mBookList;

    public BooksRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setBookList(ArrayList<String[]> listLiveBioData) {
        mBookList = listLiveBioData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mBookList.size() > 0) {
            holder.displayBook(position);
            holder.mCurrentPosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mBookTitle;
        private TextView mBookDatePublished;
        private TextView mBookAuthors;
        private TextView mBookPublisher;
//        private ImageView mBookImage;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBookTitle = itemView.findViewById(R.id.txt_book_title);
            mBookAuthors = itemView.findViewById(R.id.txt_authors);
//            mBookImage = itemView.findViewById(R.id.imageView);
            mBookDatePublished = itemView.findViewById(R.id.txt_date_publishe);
            mBookPublisher = itemView.findViewById(R.id.txt_publisher);
        }

        public void displayBook(int position) {
            String[] book= mBookList.get(position);
            mBookTitle.setText(book[0]);
            mBookAuthors.setText(book[1]);
            mBookDatePublished.setText(book[2]);
            mBookPublisher.setText(book[3]);
        }
    }
}
