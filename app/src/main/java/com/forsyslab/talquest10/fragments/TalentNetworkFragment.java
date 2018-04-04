package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.TalentNetworkAdapter;
import com.forsyslab.talquest10.model.User;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 04/04/2017.
 */

public class TalentNetworkFragment extends Fragment {


    private RecyclerView recyclerView;
    private TalentNetworkAdapter talentAdapter;
    private List<User> talents = new ArrayList<>();
    private List<Integer> imagesId = new ArrayList<>();

    public TalentNetworkFragment(List<User> talents,List<Integer> imagesId) {
        this.talents = talents;
        this.imagesId = imagesId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talent_network, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        talentAdapter = new TalentNetworkAdapter(getContext(), getActivity());
        talentAdapter.setUserList(talents);
        talentAdapter.setImageId(imagesId);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(talentAdapter);

        return view;
    }

}
