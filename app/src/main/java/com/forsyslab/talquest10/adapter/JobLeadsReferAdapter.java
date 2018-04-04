package com.forsyslab.talquest10.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.dagger.PutJobLead;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.ModelDto.JobLeadsDto;
import com.forsyslab.talquest10.model.Reference;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.ramotion.foldingcell.FoldingCell;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.HashSet;
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
 * Created by LENOVO on 05/04/2017.
 */

public class JobLeadsReferAdapter extends RecyclerView.Adapter<JobLeadsReferAdapter.MyViewHolder> {


    private List<JobLeads> jobList;
    private Context context;
    String referredLogin;
    String referredByLogin;

    FragmentActivity activity;

    @Inject
    public JobLeadsReferAdapter(Context context, FragmentActivity activity,String referredLogin,String referredByLogin) {
        this.context = context;
        this.activity = activity;
        this.referredLogin = referredLogin;
        this.referredByLogin = referredByLogin;
    }

    public void setJobList(List<JobLeads> jobList) {
        this.jobList = jobList;
        this.notifyDataSetChanged();
    }

    @Override
    public JobLeadsReferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_leads_refer, parent, false);
        return new JobLeadsReferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobLeadsReferAdapter.MyViewHolder holder, int position) {
        holder.companyTitle.setText(jobList.get(position).getTitle());
        holder.salary.setText(jobList.get(position).getSalary() + "$");
        //  holder.jobTitle.setText(jobList.get(position).getJobTitle());
        //  holder.jobTitle1.setText(jobList.get(position).getJobTitle());
        holder.salary1.setText(jobList.get(position).getSalary() + "$");


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Inject
        Retrofit retrofit;


        @BindView(R.id.companyTitle)
        TextView companyTitle;
        @BindView(R.id.salary)
        TextView salary;
        @BindView(R.id.jobTitle)
        TextView jobTitle;
        @BindView(R.id.salary1)
        TextView salary1;
        @BindView(R.id.jobTitle1)
        TextView jobTitle1;
        @BindView(R.id.jobSector)
        TextView jobSector;
        @BindView(R.id.company_desc)
        TextView companyDescription;
        @BindView(R.id.mission_desc)
        TextView missionDescription;
        @BindView(R.id.profilSearch)
        TextView profilSearch;
        LinearLayout refer;
        ImageView companyIcon;
        FoldingCell fc;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            refer = (LinearLayout) view.findViewById(R.id.refer);
            companyIcon = (ImageView) view.findViewById(R.id.companyIcon);


            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });
            refer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AndroidApplication) getApplicationContext()).getNetComponent().inject(MyViewHolder.this);
                    Set<Reference> references = jobList.get(getPosition()).getReferenceList();

                    Gson gson = new Gson();
                    Log.d("nizarab",gson.toJson(jobList.get(getPosition())));
                    if (null == references) {
                        references = new HashSet<>();
                    }
                    references.add(new Reference(referredLogin+referredByLogin, referredLogin,referredByLogin));
                    jobList.get(getPosition()).setReferenceList(references);

                    Log.d("nizarab",jobList.get(getPosition()).getReferenceList().iterator().next().getId());
                    final Call<JobLeads> jobLeadsCall = retrofit.create(PutJobLead.class).putJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), jobList.get(getPosition()));
                    jobLeadsCall.enqueue(new Callback<JobLeads>() {
                        @Override
                        public void success(Result<JobLeads> result) {

                        }
                        @Override
                        public void failure(TwitterException exception) {
                            Log.d("nizarab",exception.toString());
                        }
                    });
                    new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Refer")
                            .setContentText("your friend has been Reference")
                            .setConfirmText("Ok").show();
                }
            });


        }


    }
}