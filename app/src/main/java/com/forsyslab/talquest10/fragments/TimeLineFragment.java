package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import java.lang.reflect.Type;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.JobLeadsAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.TAG;

/**
 * Created by LENOVO on 23/02/2017.
 */


public class TimeLineFragment extends Fragment {

    public static final String A_NETWORK_ERROR_HAS_OCCURRED = "A network error has occurred";

    JobLeadsAdapter jobLeadsAdapter;
    @Inject
    Retrofit retrofit;

    private RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<JobLeads> jobLeadsList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        jobLeadsAdapter = new JobLeadsAdapter(getContext(), getActivity());
        jobLeadsAdapter.setJobList(jobLeadsList);


        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getTimeLine();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "refresh", Toast.LENGTH_LONG).show();
                        getTimeLine();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        return view;
    }

    private void getTimeLine() {
        final Call<List<JobLeads>> jobLeadList = retrofit.create(GetJobLeads.class).getJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        jobLeadList.enqueue(new Callback<List<JobLeads>>() {
            @Override
            public void success(Result<List<JobLeads>> result) {
                jobLeadsList = result.data;
                jobLeadsAdapter.setJobList(jobLeadsList);
                mRecyclerView.setAdapter(jobLeadsAdapter);
                Gson gson = new Gson();
                String jobLeadsListString = gson.toJson(jobLeadsList);
                Log.d("nizarjoblead",jobLeadsListString);
                PreferencesStorageService.setDefaults(JOB_LEADS, jobLeadsListString, getApplicationContext());
            }
            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(),A_NETWORK_ERROR_HAS_OCCURRED+"",Toast.LENGTH_LONG).show();
                Log.d(TAG,exception.toString());
                Gson gson = new Gson();
                String jobLeadsListGetString = PreferencesStorageService.getDefaults(JOB_LEADS, getApplicationContext());
                Type type = new TypeToken<List<JobLeads>>() {
                }.getType();
                List<JobLeads> jobs = gson.fromJson(jobLeadsListGetString, type);
                if(jobs!=null){
                    jobLeadsAdapter.setJobList(jobs);
                    Log.d("nizarab",jobs.toString());
                }
                mRecyclerView.setAdapter(jobLeadsAdapter);
            }

        });
    }
}
