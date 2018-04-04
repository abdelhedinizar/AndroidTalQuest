package com.forsyslab.talquest10.dagger.component;

/**
 * Created by LENOVO on 27/03/2017.
 */


import com.forsyslab.talquest10.AccueilActivity;
import com.forsyslab.talquest10.FirstActivity;
import com.forsyslab.talquest10.MainActivity;
import com.forsyslab.talquest10.WelcomeActivity;
import com.forsyslab.talquest10.adapter.JobLeadsReferAdapter;
import com.forsyslab.talquest10.adapter.ListReferenceAdapter;
import com.forsyslab.talquest10.dagger.module.AppModule;
import com.forsyslab.talquest10.dagger.module.NetModule;
import com.forsyslab.talquest10.fragments.DisplayReferenceList;
import com.forsyslab.talquest10.fragments.JobleadsListFragment;
import com.forsyslab.talquest10.fragments.MyJobLeadsFragment;
import com.forsyslab.talquest10.fragments.PostJobLeadFragment;
import com.forsyslab.talquest10.fragments.profils.FriendProfilDescription;
import com.forsyslab.talquest10.fragments.profils.ProfilCompanyFragment;
import com.forsyslab.talquest10.fragments.profils.ProfilFragment;
import com.forsyslab.talquest10.fragments.ReferFragment;
import com.forsyslab.talquest10.fragments.signUp.company.SignUpCompanyStep4;
import com.forsyslab.talquest10.fragments.signUp.npo.SignUpNGOStep4;
import com.forsyslab.talquest10.fragments.signUp.personalUser.SignUpPersonalUserStep4;
import com.forsyslab.talquest10.fragments.TalentFragment;
import com.forsyslab.talquest10.fragments.TimeLineFragment;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(JobLeadsReferAdapter.MyViewHolder adapter);

    void inject(ListReferenceAdapter listReferenceHolder);

    void inject(MyJobLeadsFragment myJobLeadsFragment);

    void inject(SignUpNGOStep4 fragment);

    void inject(JobleadsListFragment fragment);

    void inject(AccueilActivity activity);

    void inject(TalentFragment fragment);

    void inject(SignUpPersonalUserStep4 fragment);

    void inject(ReferFragment fragment);

    void inject(WelcomeActivity activity);

    void inject(TimeLineFragment fragment);

    void inject(MainActivity activity);

    void inject(ProfilCompanyFragment fragment);

    void inject(ProfilFragment fragment);

    void inject(FirstActivity activity);

    void inject(SignUpCompanyStep4 signUpCompanyStep4);

    void inject(PostJobLeadFragment jobLeadFragment);

    void inject(FriendProfilDescription friendProfilDescription);
}
