package com.forsyslab.talquest10.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.User;
import com.mikepenz.materialdrawer.view.BezelImageView;

import java.util.List;

/**
 * Created by LENOVO on 04/04/2017.
 */

public class TalentNetworkAdapter extends RecyclerView.Adapter<TalentNetworkAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> userList;
    private List<Integer> imageId;
    private FragmentActivity activity;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setImageId(List<Integer> imageId) {
        this.imageId = imageId;
    }

    public TalentNetworkAdapter(Context mContext, FragmentActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.talent_network_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User talent = userList.get(position);
        holder.first_text_view.setText(talent.getFirstName() + " " + talent.getLastName());
        holder.second_text_view.setText(talent.getJobTitle());
        holder.talentImage.setImageResource(imageId.get(position));
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout two_line_item;
        public TextView first_text_view, second_text_view;
        public BezelImageView talentImage;

        public MyViewHolder(View view) {
            super(view);
            two_line_item = (RelativeLayout) view.findViewById(R.id.two_line_item);
            first_text_view = (TextView) view.findViewById(R.id.first_text_view);
            second_text_view = (TextView) view.findViewById(R.id.second_text_view);
            talentImage = (BezelImageView) view.findViewById(R.id.talentImage);

        }
    }

}