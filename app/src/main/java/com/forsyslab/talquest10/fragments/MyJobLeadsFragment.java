package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.forsyslab.talquest10.adapter.MyJobLeadsAdapter;
import com.forsyslab.talquest10.adapter.TalentAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by abdelhedi on 06/06/2017.
 */

public class MyJobLeadsFragment extends Fragment {

    MyJobLeadsAdapter myJobLeadsAdapter;
    private RecyclerView recyclerView;
    private TalentAdapter talentAdapter;
    private List<User> talents = new ArrayList<>();

    @Inject
    Retrofit retrofit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_jobleads, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myjobleads);
        myJobLeadsAdapter = new MyJobLeadsAdapter(getActivity());
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        final Call<List<JobLeads>> jobLeadsCall = retrofit.create(GetJobLeads.class).getJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));

        jobLeadsCall.enqueue(new Callback<List<JobLeads>>() {
            @Override
            public void success(Result<List<JobLeads>> result) {
                List<JobLeads> jobLeadsList = new ArrayList<>();
                for (JobLeads jobLeads:result.data)
                {
                    if(jobLeads.getCompanyName()!=null)
                    {
                        if(jobLeads.getCompanyName().equals(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN,getApplicationContext())))
                        {
                            jobLeadsList.add(jobLeads);
                        }
                    }
                }
                myJobLeadsAdapter.setJobList(jobLeadsList);
                LinearLayoutManager mLinearLayoutManager;
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLinearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(myJobLeadsAdapter);
            }

            @Override
            public void failure(TwitterException exception) {

                Gson gson = new Gson();
                String jobLeadsListGetString = PreferencesStorageService.getDefaults(JOB_LEADS, getApplicationContext());
                Log.d("nizarab",jobLeadsListGetString);
                Type type = new TypeToken<List<JobLeads>>() {
                }.getType();
                List<JobLeads> jobs = gson.fromJson(jobLeadsListGetString, type);
                if(jobs!=null){
                    myJobLeadsAdapter.setJobList(jobs);
                    Log.d("nizarab",jobs.toString());
                }
                LinearLayoutManager mLinearLayoutManager;
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLinearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(myJobLeadsAdapter);

            }
        });
        return view;
    }
}
