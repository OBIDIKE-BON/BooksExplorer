package com.stackfloat.booksexplorer;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoadImageWithPicasso {
    private LoadImageWithPicasso(){}

    /**
     * gradle plugin=implementation 'com.squareup.picasso:picasso:(insert latest version)'
     *
     * this method loads image into an ImageView
     *
     * @param view @ImageView to load the image into
     * @param imageUrl url of the image to be loaded
     * @param resourceId ResourceId of a placeHolder image
     */
    public static void loadImage(@NotNull ImageView view, String imageUrl, int resourceId){
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(resourceId)
                .into(view);
    }
}
