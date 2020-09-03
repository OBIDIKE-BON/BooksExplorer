package com.stackfloat.booksexplorer;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Book implements Parcelable {

    public String id;
    public String title;
    public String subTitle;
    public String dateOfPublication;
    public String publisher;
    public String authors;
    public String description;
    public String thumbnail;

    public Book(String id, String title, String subTitle,
                String dateOfPublication, String publisher, String authors,
                String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.dateOfPublication = dateOfPublication;
        this.publisher = publisher;
        this.authors = authors;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        dateOfPublication = in.readString();
        publisher = in.readString();
        authors = in.readString();
        description = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(dateOfPublication);
        parcel.writeString(publisher);
        parcel.writeString(authors);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
    }

    @BindingAdapter("android:imageUrl")
    public static void getImageUrl(ImageView view, String imageUrl) {
        LoadImageWithPicasso.loadImage(view, imageUrl, R.drawable.ic_action_name);
    }
}

