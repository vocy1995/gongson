package com.example.androids.callback;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface AsyncCallback<T> {
    public void onSuccess(ImageView object);
    public void onFailure(Exception e);
}