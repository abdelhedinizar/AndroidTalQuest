package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetAllProfil;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.PersonalProfil;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 02/03/2017.
 */

import com.forsyslab.talquest10.adapter.FriendsAdapter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.TAG;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

public class ReferFragment extends Fragment {

    @Inject
    Retrofit retrofit;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refer, container, false);

        final ListView friendsList = (ListView) view.findViewById(R.id.friends);
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        final Call<List<User>> jobLeadList = retrofit.create(GetAllProfil.class).getUsers(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));

        jobLeadList.enqueue(new Callback<List<User>>() {
            FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();

            @Override
            public void success(Result<List<User>> result) {
                List<User> profils = new ArrayList<>();
                for (User user : result.data) {
                    if (user.getType() != null) {
                        if (user.getType().equals(USER_KEY)) {
                            profils.add(user);
                        }
                    }
                }
                friendsList.setAdapter(new FriendsAdapter(getActivity(), profils, settings));
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonalProfil f = (PersonalProfil) friendsList.getAdapter().getItem(position);

                Toast.makeText(getActivity(), f.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
