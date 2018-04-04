package com.forsyslab.talquest10.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.PersonalProfil;
import com.forsyslab.talquest10.model.User;
import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by LENOVO on 28/02/2017.
 */

public class FriendsAdapter extends BaseFlipAdapter<User>{

    private final int PAGES = 3;
    Activity context;
    public FriendsAdapter(FragmentActivity context, List<User> items, FlipSettings settings) {
        super(context, items, settings);
        this.context=context;
    }


    @Override
    public View getPage(int position, View convertView, ViewGroup parent, User friend1, User friend2) {
        final FriendsHolder holder;

        if (convertView == null) {
            holder = new FriendsHolder();
            convertView = context.getLayoutInflater().inflate(R.layout.friends_view_item, parent, false);
            holder.leftAvatar = (ImageView) convertView.findViewById(R.id.first);
            holder.rightAvatar = (ImageView) convertView.findViewById(R.id.second);
            holder.infoPage = context.getLayoutInflater().inflate(R.layout.additional_view, parent, false);
            holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);
            holder.refer = (Button) holder.infoPage.findViewById(R.id.refer);

            convertView.setTag(holder);
        } else {
            holder = (FriendsHolder) convertView.getTag();
        }

        switch (position) {
            // Merged page with 2 friends
            case 1:
                holder.leftAvatar.setImageResource((R.drawable.myphoto));
                if (friend2 != null)
                    holder.rightAvatar.setImageResource((R.drawable.myphoto));
                break;
            default:
                fillHolder(holder, position == 0 ? (User) friend1 : (User) friend2);
                holder.infoPage.setTag(holder);
                return holder.infoPage;
        }
        return convertView;
    }


    @Override
    public int getPagesCount() {
        return PAGES;
    }

    private void fillHolder(FriendsHolder holder, User friend) {
        if (friend == null)
            return;
        Iterator<TextView> iViews = holder.interests.iterator();
        while (iViews.hasNext())
        holder.infoPage.setBackgroundColor((Color.argb(255,new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255))));
        holder.nickName.setText(friend.getLogin());
        holder.refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("nizar","nizar");
            }
        });

    }

    class FriendsHolder {
        ImageView leftAvatar;
        ImageView rightAvatar;
        View infoPage;
        Button refer;

        List<TextView> interests = new ArrayList<>();
        TextView nickName;
    }
}

