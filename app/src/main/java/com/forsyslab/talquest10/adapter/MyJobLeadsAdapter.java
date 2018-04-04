package com.forsyslab.talquest10.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.fragments.DisplayReferenceList;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.services.UserInputVerification;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abdelhedi on 13/06/2017.
 */

public class MyJobLeadsAdapter extends RecyclerView.Adapter<MyJobLeadsAdapter.MyViewHolder> {

    private List<JobLeads> jobList;
    FragmentActivity activity;


    @Inject
    public MyJobLeadsAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setJobList(List<JobLeads> jobList) {
        this.jobList = jobList;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_job_leads_item, parent, false);
        return new MyJobLeadsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.companyTitle.setText(jobList.get(position).getCompanyName());
        holder.salary.setText(jobList.get(position).getSalary() + "$");
        holder.jobTitle.setText(jobList.get(position).getTitle());
        holder.jobTitle1.setText(jobList.get(position).getTitle());
        holder.salary1.setText(jobList.get(position).getSalary() + "$");
        holder.rewardchoosen.setText(jobList.get(position).getRewardChoosen() + "$");
        holder.experience.setText(jobList.get(position).getExperience());
        holder.jobSector.setText(jobList.get(position).getSector());
        holder.companyDescription.setText(UserInputVerification.showTextViewComparedToLenght(jobList.get(position).getProfileSearch(), 120));
        holder.profilSearch.setText(UserInputVerification.showTextViewComparedToLenght(jobList.get(position).getProfileSearch(), 120));
        holder.talents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jobList.get(position).getReferenceList().iterator().hasNext())
                {
                    Toast.makeText(activity,jobList.get(position).getReferenceList().iterator().next().getReferredLogin()+" ",Toast.LENGTH_LONG).show();
                }

                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                DisplayReferenceList timeLineFragment = new DisplayReferenceList(jobList.get(position));
                transaction.addToBackStack("");
                transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        FoldingCell fc;
        @BindView(R.id.companyTitle)
        TextView companyTitle;
        @BindView(R.id.salary)
        TextView salary;

        @BindView(R.id.jobTitle)
        TextView jobTitle;
        @BindView(R.id.jobTitle1)
        TextView jobTitle1;
        @BindView(R.id.rewardchoosen)
        TextView rewardchoosen;
        @BindView(R.id.experience)
        TextView experience;

        @BindView(R.id.salary1)
        TextView salary1;
        @BindView(R.id.jobSector)
        TextView jobSector;
        @BindView(R.id.company_desc)
        TextView companyDescription;
        @BindView(R.id.profilSearch)
        TextView profilSearch;

        ImageView talents;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            talents =(ImageView) view.findViewById(R.id.talents);
            fc = (FoldingCell) view.findViewById(R.id.folding_cell);

            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });
        }
    }
}
