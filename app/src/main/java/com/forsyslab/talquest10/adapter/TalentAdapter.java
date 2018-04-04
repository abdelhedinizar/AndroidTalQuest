package com.forsyslab.talquest10.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.fragments.profils.FriendProfilDescription;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.ImageService;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;
import com.mikepenz.materialdrawer.view.BezelImageView;

import java.util.List;

/**
 * Created by LENOVO on 03/04/2017.
 */

public class TalentAdapter extends RecyclerView.Adapter<TalentAdapter.MyViewHolder> {

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

    public TalentAdapter(Context mContext, FragmentActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.talent_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final User talent = userList.get(position);
        if(null!=talent.getLogo())
        {
            byte[] imageAsBytes = Base64.decode(talent.getLogo().getBytes(), Base64.DEFAULT);
            holder.talentImage.setImageBitmap(
                    ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
            );
        }

        holder.first_text_view.setText(talent.getFirstName() + " " + talent.getLastName());
        holder.second_text_view.setText(talent.getJobTitle());
   //test/     holder.talentImage.setImageResource(imageId.get(position));
        holder.two_line_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendProfilDescription profilDescription = new FriendProfilDescription(talent);
                FragmentTransitionLauncher
                        .with(view.getContext())
                        .image(BitmapFactory.decodeResource(mContext.getResources(), imageId.get(position)))
                        .from(view.findViewById(R.id.talentImage)).prepare(profilDescription);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.accueilLineaireLayout, profilDescription).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout two_line_item;
        public TextView first_text_view, second_text_view;
        public BezelImageView talentImage;
        public ImageView connectTalent;

        public MyViewHolder(View view) {
            super(view);
            two_line_item = (RelativeLayout) view.findViewById(R.id.two_line_item);
            first_text_view = (TextView) view.findViewById(R.id.first_text_view);
            second_text_view = (TextView) view.findViewById(R.id.second_text_view);
            talentImage = (BezelImageView) view.findViewById(R.id.talentImage);
            connectTalent = (ImageView) view.findViewById(R.id.connectTalent);

        }
    }

}
