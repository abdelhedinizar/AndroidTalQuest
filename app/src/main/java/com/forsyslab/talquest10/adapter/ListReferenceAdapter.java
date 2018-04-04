package com.forsyslab.talquest10.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.easing.Skill;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetProfil;
import com.forsyslab.talquest10.dagger.PutProfil;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.Reference;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.ImageService;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.view.BezelImageView;
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
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by abdelhedi on 10/07/2017.
 */

public class ListReferenceAdapter extends RecyclerView.Adapter<ListReferenceAdapter.ListReferenceHolder> {


    private Set<Reference> referenceList;
    private ArrayList<Reference> referenceArrayList = new ArrayList<>();
    JobLeads jobLeads;
    private List<User> referUser;
    private List<User> referByUser;

    @Inject
    Retrofit retrofit;


    public ListReferenceAdapter(JobLeads jobLeads, List<User> referUser, List<User> referByUser) {
        this.jobLeads = jobLeads;
        referenceList = jobLeads.getReferenceList();
        this.referUser = referUser;
        this.referByUser = referByUser;

        Iterator<Reference> it = referenceList.iterator();
        while (it.hasNext()) {
            referenceArrayList.add(it.next());
        }

        Gson gson = new Gson();
        Log.d("nizarabdelhedi", gson.toJson(referenceArrayList));
    }


    @Override
    public ListReferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reference_list_item, parent, false);
        return new ListReferenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListReferenceHolder holder, int position) {

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        Call<User> nGOProfil = retrofit.create(GetProfil.class).getUser(jobLeads.getNgo(), BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        nGOProfil.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                Gson gson = new Gson();
                PreferencesStorageService.setDefaults("NgoProfil", gson.toJson(result.data), getApplicationContext());

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        Log.d("nizarab", position + "");
        Call<User> refferedProfilCall = retrofit.create(GetProfil.class).getUser(referenceArrayList.get(position).getReferredLogin(), BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        final Call<User> refferedByProfilCall = retrofit.create(GetProfil.class).getUser(referenceArrayList.get(position).getReferredByLogin(), BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));

        refferedProfilCall.enqueue(new Callback<User>() {
            @Override
            public void success(final Result<User> result) {
                //      holder.referByName.setText(result.data.getFirstName() +" " +result.data.getLastName());

                Gson gson = new Gson();
                PreferencesStorageService.setDefaults("referedProfil", gson.toJson(result.data), getApplicationContext());

                if (result.data.getExperience() != null) {
                    holder.experience.setText("Experience : " + result.data.getExperience());
                }

                if (null != result.data.getSkills()) {
                    for (String str : result.data.getSkills()) {
                        TextView skillsTextView = new TextView(getApplicationContext());
                        skillsTextView.setText(str);
                        skillsTextView.setTextColor(Color.WHITE);
                        skillsTextView.setBackgroundColor(Color.BLACK);
                        skillsTextView.setPadding(5, 0, 5, 0);
                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        llp.setMargins(10, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
                        skillsTextView.setLayoutParams(llp);
                        holder.skillsLineaireLayout.addView(skillsTextView);
                    }
                }
            }

            @Override
            public void failure(TwitterException exception) {
                //     referByUser.add(new User());
            }
        });


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                User referedByUser = gson.fromJson(PreferencesStorageService.getDefaults("referedByProfil", getApplicationContext()), User.class);
                Log.d("nizarTalQuest", referedByUser.getLogin());

                User referedUser = gson.fromJson(PreferencesStorageService.getDefaults("referedProfil", getApplicationContext()), User.class);


                Double referedSolde = 0.0;
                if (referedByUser.getSolde() != null) {
                    referedSolde += Double.parseDouble(referedByUser.getSolde());
                }
                Double rewardChoosen = Double.parseDouble(jobLeads.getRewardChoosen());

                referedByUser.setSolde((referedSolde + rewardChoosen) + "");
                Log.d("nizarTalQuest1", referedByUser.getSolde());
                PreferencesStorageService.setDefaults("referedByProfil", gson.toJson(referedByUser), getApplicationContext());


                holder.referPersonName.setText(referedUser.getFirstName() + " " + referedUser.getLastName());

                if (referedUser.getLogo() != null) {
                    byte[] imageAsBytes = Base64.decode(referedUser.getLogo().getBytes(), Base64.DEFAULT);
                    holder.referPerson.setImageBitmap(
                            ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
                    );
                } else {
                    holder.referPerson.setImageResource(R.drawable.ic_personimage);
                }

                Log.d("nizarab2", PreferencesStorageService.getDefaults("referedByProfil", getApplicationContext()));
                if (referedByUser != null) {
                    Call<User> refferedByProfilPutCall = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), referedByUser);
                    refferedByProfilPutCall.enqueue(new Callback<User>() {
                        @Override
                        public void success(Result<User> result) {
                            Log.d("nizarTalQuest2", "succes");
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            Log.d("nizarTalQuest2", "failed");

                        }
                    });

                }

                User ngoUser = gson.fromJson(PreferencesStorageService.getDefaults("NgoProfil", getApplicationContext()), User.class);
                Double referedSoldeNGO = 0.0;
                if (ngoUser.getSolde() != null) {
                    referedSoldeNGO += Double.parseDouble(referedByUser.getSolde());
                }
                ngoUser.setSolde((referedSoldeNGO + rewardChoosen) + "");


                Call<User> putNgoProfil = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), ngoUser);
                putNgoProfil.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
               Log.d("nizarabdel","succes");
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("nizarabdel","failed");

                    }
                });


            }
        });


        refferedByProfilCall.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                holder.referByName.setText(result.data.getFirstName() + " " + result.data.getLastName());
                Gson gson = new Gson();
                PreferencesStorageService.setDefaults("referedByProfil", gson.toJson(result.data), getApplicationContext());

                if (result.data.getLogo() != null) {
                    byte[] imageAsBytes = Base64.decode(result.data.getLogo().getBytes(), Base64.DEFAULT);
                    holder.referredByImage.setImageBitmap(
                            ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
                    );
                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return referenceArrayList.size();
    }

    public class ListReferenceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.referPerson)
        BezelImageView referPerson;
        @BindView(R.id.referByPerson)
        BezelImageView referredByImage;
        @BindView(R.id.referPersonName)
        TextView referPersonName;
        @BindView(R.id.referByName)
        TextView referByName;
        @BindView(R.id.experience)
        TextView experience;
        @BindView(R.id.skillsLineaireLayout)
        LinearLayout skillsLineaireLayout;
        @BindView(R.id.accept)
        Button accept;


        public ListReferenceHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}
