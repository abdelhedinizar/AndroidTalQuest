package com.forsyslab.talquest10.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.ListReferenceAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetProfil;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.Reference;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by abdelhedi on 08/07/2017.
 */
public class DisplayReferenceList extends Fragment {



    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Set<Reference> referenceList;
    private LinearLayoutManager mLinearLayoutManager;
    JobLeads jobLeads;

    List<User> referUser = new ArrayList<>();
    List<User> referByUser = new ArrayList<>();

    public DisplayReferenceList(JobLeads jobLeads) {
        this.jobLeads = jobLeads;
        referenceList = jobLeads.getReferenceList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_reference_list, container, false);
        ButterKnife.bind(this, view);



        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Iterator<Reference> iterator = referenceList.iterator();

        while (iterator.hasNext()) {

            final Reference reference = iterator.next();

/*            refferedByProfilCall.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    referByUser.add(result.data);
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.d("nizarabd","failed "+reference.getReferredByLogin());
                    referByUser.add(new User());
                }
            });
            */
        }
        pDialog.cancel();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ListReferenceAdapter referenceAdapter = new ListReferenceAdapter(jobLeads,referUser,referByUser);
        mRecyclerView.setAdapter(referenceAdapter);
        return view;
    }
}
