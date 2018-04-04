package com.forsyslab.talquest10.adapter.Helper;

/**
 * Created by LENOVO on 02/05/2017.
 */

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.forsyslab.talquest10.model.JobLeads;


public class GlideHelper {

    private GlideHelper() {}

    public static void loadPaintingImage(ImageView image,int jobImageId) {
        Glide.with(image.getContext().getApplicationContext())
                .load(jobImageId)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }

}
