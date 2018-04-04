package com.forsyslab.talquest10.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.fragments.ReferFragment;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.services.UserInputVerification;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by LENOVO on 17/02/2017.
 */

public class JobLeadsAdapter extends RecyclerView.Adapter<JobLeadsAdapter.MyViewHolder> {


    private List<JobLeads> jobList;
    private Context context;
    FragmentActivity activity;

    @Inject
    public JobLeadsAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setJobList(List<JobLeads> jobList) {
        this.jobList = jobList;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_leads_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.companyTitle.setText(jobList.get(position).getCompanyName());
        holder.salary.setText(jobList.get(position).getSalary() + "$");
        holder.jobTitle.setText(jobList.get(position).getTitle());
        holder.jobTitle1.setText(jobList.get(position).getTitle());
        holder.salary1.setText(jobList.get(position).getSalary() + "$");
        holder.rewardchoosen.setText(jobList.get(position).getRewardChoosen()+"$");
        holder.experience.setText(jobList.get(position).getExperience());
        holder.jobSector.setText(jobList.get(position).getSector());
        holder.companyDescription.setText(UserInputVerification.showTextViewComparedToLenght(jobList.get(position).getProfileSearch(),120));
        holder.profilSearch.setText(UserInputVerification.showTextViewComparedToLenght(jobList.get(position).getProfileSearch(),120));

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
        LinearLayout withdraw, saveforlater, refer;
        ImageView companyIcon;
        FoldingCell fc;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            withdraw = (LinearLayout) view.findViewById(R.id.withdraw);
            saveforlater = (LinearLayout) view.findViewById(R.id.saveforlater);
            refer = (LinearLayout) view.findViewById(R.id.refer);
            companyIcon = (ImageView) view.findViewById(R.id.companyIcon);


            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });

            withdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    new MaterialDialog.Builder(context)
                            .title("Confirm Hide")
                            .content("Are you sure you want hide the selected job")
                            .positiveText("Yes")
                            .negativeText("No")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    int position = getPosition();
                                    jobList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, jobList.size());

                                }
                            })
                            .show();
                            */
                    new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Confirm Hide")
                            .setContentText("Are you sure you want hide the selected job")
                            .setConfirmText("Yes").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            int position = getPosition();
                            jobList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, jobList.size());
                            sweetAlertDialog.cancel();
                        }
                    })
                            .setCancelText("No").show();
                }
            });

            saveforlater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Job")
                            .setContentText("This job has been saved")
                            .setConfirmText("Ok").show();

                }
            });

            refer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              /*     new MaterialDialog.Builder(context)
                            .title("Choose your friend to refer")
                            .adapter(new FriendsAdapter(activity,profils,settings))
                            .positiveText("Refer")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    new MaterialDialog.Builder(context)
                                            .content("Your friend has been refrred")
                                            .positiveText("Ok")
                                            .show();
                                }
                            })
                            .show();
                */

                    FragmentManager manager = activity.getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    ReferFragment referFragment = new ReferFragment();
                    transaction.addToBackStack("");
                    transaction.replace(R.id.accueilLineaireLayout, referFragment);
                    transaction.commit();


                }
            });


        }


    }

}
