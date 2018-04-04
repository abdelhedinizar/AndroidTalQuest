package com.forsyslab.talquest10.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.forsyslab.talquest10.AccueilCompanyActivity;

/**
 * Created by abdelhedi on 01/06/2017.
 */

public class ImageService {

    ImageService(){
    }
    public static Bitmap resize(Bitmap bitmap) {
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
        return  bitmapResized;
    }

    public static Drawable resize(Drawable image, Context context) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 500, 500, false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }


}
