package com.example.studio.Controller;

import android.widget.ImageView;

import com.example.studio.R;

public class RoomImagesController {
    private int[] imageArray = {R.drawable.rooma, R.drawable.roomb,
            R.drawable.roomc, R.drawable.roomd,
            R.drawable.roome, R.drawable.roomf,
            R.drawable.roomg, R.drawable.roomh};

    private int currentIndex;

    public RoomImagesController() {
        this.currentIndex = 0;
    }

    public void setImage(ImageView imageView) {
        if (currentIndex >= 0 && currentIndex < imageArray.length) {
            imageView.setImageResource(imageArray[currentIndex]);
        }
    }

    public void nextImage() {
        currentIndex++;
        if (currentIndex >= imageArray.length) {
            currentIndex = 0;
        }
    }

    public void previousImage() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = imageArray.length - 1;
        }
    }
}
