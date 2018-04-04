package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.JobLeadsAdapter;
import com.forsyslab.talquest10.adapter.JobLeadsReferAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetAllProfil;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by LENOVO on 05/04/2017.
 */

public class JobleadsListFragment extends Fragment {


    String userLogin;

    public JobleadsListFragment(String userLogin) {
        this.userLogin = userLogin;
    }

    @Inject
    Retrofit retrofit;

    JobLeadsReferAdapter jobLeadsReferAdapter;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobleads_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
        jobLeadsReferAdapter = new JobLeadsReferAdapter(getContext(), getActivity(),userLogin,PreferencesStorageService.getDefaults(PREFERENCES_LOGIN,getContext()) );

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        final Call<List<JobLeads>> jobLeadsCall = retrofit.create(GetJobLeads.class).getJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));

        jobLeadsCall.enqueue(new Callback<List<JobLeads>>() {
            @Override
            public void success(Result<List<JobLeads>> result) {
                jobLeadsReferAdapter.setJobList(result.data);
                LinearLayoutManager mLinearLayoutManager;
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(jobLeadsReferAdapter);

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });


        return view;
    }
}
