package com.forsyslab.talquest10.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.utils.Views;
import com.forsyslab.talquest10.AccueilActivity;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.Helper.GlideHelper;
import com.forsyslab.talquest10.model.JobLeads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 02/05/2017.
 */

public class HomeAdapter extends ItemsAdapter<JobLeads> implements View.OnClickListener {

    List<JobLeads> jobLeadses = new ArrayList<>();
    int[] postWallpaper = {R.drawable.home_background, R.drawable.microsoft_wallpaper, R.drawable.facebook_background};

    public HomeAdapter(Context context,List<JobLeads> jobLeadses) {
        super(context);
        this.jobLeadses = jobLeadses;
     //   jobLeadses.add(new JobLeads("Android developer job", "Android developer job", "33000 $", "recherche pour le compte de l'un de ses clients un Développeur Android", "Participer à la mise en place de l'architecture de la plate-forme", "", ""));
     //   jobLeadses.add(new JobLeads("Xamarin developer job", "Xamarin developer job", "33000 $", "", "", "", ""));
     //   jobLeadses.add(new JobLeads("web dev", "web dev", "33000 $", "", "", "", ""));
        setItemsList(jobLeadses);
    }

    @Override
    protected View createView(JobLeads item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.home_list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = Views.find(view, R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = Views.find(view, R.id.list_item_title);
        view.setTag(vh);
        final ImageView likeImage = vh.likeImage = (ImageView) view.findViewById(R.id.likeimage);
        likeImage.setOnClickListener(new View.OnClickListener() {
            Boolean enable = false;
            @Override
            public void onClick(View view) {
                if (enable)
                    likeImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.blue));
                else
                {
                    likeImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.gris));
                }
                enable=!enable;
  }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        JobLeads item = (JobLeads) view.getTag(R.id.list_item_image);
        int wallpaperImage = (Integer) view.getTag(R.id.list_item_title);
        ((AccueilActivity) view.getContext()).openDetails(view, item, wallpaperImage);
    }

    @Override
    protected void bindView(JobLeads item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.image.setTag(R.id.list_item_image, item);
        vh.image.setTag(R.id.list_item_title, postWallpaper[pos%3]);
        GlideHelper.loadPaintingImage(vh.image, postWallpaper[pos%3]);
        vh.title.setText(item.getTitle());
    }


    private static class ViewHolder {
        ImageView image;
        ImageView likeImage;
        TextView title;
    }
}
