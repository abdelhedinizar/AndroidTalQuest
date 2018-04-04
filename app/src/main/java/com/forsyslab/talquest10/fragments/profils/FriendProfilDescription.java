package com.forsyslab.talquest10.fragments.profils;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetProfil;
import com.forsyslab.talquest10.fragments.JobleadsListFragment;
import com.forsyslab.talquest10.fragments.TalentNetworkFragment;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.ImageService;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.kogitune.activity_transition.fragment.ExitFragmentTransition;
import com.kogitune.activity_transition.fragment.FragmentTransition;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.nightonke.boommenu.BoomMenuButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by LENOVO on 31/03/2017.
 */

public class FriendProfilDescription extends Fragment {
    User user;
    TextView userName;
    TextView jobTitle;
    TextView sectorProfil,companyProfil,countryProfil;
    LinearLayout sectorLinearLayout, companyLinearLayout, coutryLinearLayout;
    FloatingActionButton workButton, friendsButton, familyButton, educationButton;
    Button referButton;
    BezelImageView bezelImageView;


    @Inject
    Retrofit retrofit;

    public FriendProfilDescription(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_profil_desc, container, false);

        sectorProfil = (TextView) view.findViewById(R.id.sectorProfil);
        companyProfil = (TextView) view.findViewById(R.id.companyProfil);
        countryProfil = (TextView) view.findViewById(R.id.countryProfil);



        referButton = (Button) view.findViewById(R.id.referButton);
        bezelImageView = (BezelImageView) view.findViewById(R.id.profilImageview);
        workButton = (FloatingActionButton) view.findViewById(R.id.workButton);
        friendsButton = (FloatingActionButton) view.findViewById(R.id.friendsButton);
        familyButton = (FloatingActionButton) view.findViewById(R.id.familyButton);
        educationButton = (FloatingActionButton) view.findViewById(R.id.educationButton);
        bezelImageView.setImageResource(R.drawable.ic_personimage);
        moveViewToScreenCenter(bezelImageView);

        sectorProfil.setText(user.getSector());
        companyProfil.setText(user.getCompany());
        countryProfil.setText(user.getCountry());

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        Call<User> profilCall = retrofit.create(GetProfil.class).getUser(user.getLogin(), BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        profilCall.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {

                if (null != result.data.getLogo()) {
                    byte[] imageAsBytes = Base64.decode(result.data.getLogo().getBytes(), Base64.DEFAULT);
                    bezelImageView.setImageBitmap(
                            ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
                    );
                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobleadsListFragment fragment = new JobleadsListFragment(user.getLogin());
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide).replace(R.id.accueilLineaireLayout, fragment).addToBackStack(null).commit();

            }
        });
        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> talents = new ArrayList<>();
                List<Integer> imagesId = new ArrayList<>();
                talents.add(new User("Nizar", "abdelhedi", "IT Developer"));
                imagesId.add(R.drawable.myphoto1);
                TalentNetworkFragment talentNetworkFragment = new TalentNetworkFragment(talents, imagesId);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide).replace(R.id.accueilLineaireLayout, talentNetworkFragment).addToBackStack(null).commit();
            }
        });
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> talents = new ArrayList<>();
                List<Integer> imagesId = new ArrayList<>();
                talents.add(new User("Nizar", "abdelhedi", "IT Developer"));
                imagesId.add(R.drawable.myphoto1);
                TalentNetworkFragment talentNetworkFragment = new TalentNetworkFragment(talents, imagesId);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide).replace(R.id.accueilLineaireLayout, talentNetworkFragment).addToBackStack(null).commit();
            }
        });
        familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> talents = new ArrayList<>();
                List<Integer> imagesId = new ArrayList<>();
                talents.add(new User("Nizar", "abdelhedi", "IT Developer"));
                imagesId.add(R.drawable.myphoto1);

                TalentNetworkFragment talentNetworkFragment = new TalentNetworkFragment(talents, imagesId);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide).replace(R.id.accueilLineaireLayout, talentNetworkFragment).addToBackStack(null).commit();
            }
        });

        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> talents = new ArrayList<>();
                List<Integer> imagesId = new ArrayList<>();
                talents.add(new User("Nizar", "abdelhedi", "IT Developer"));
                imagesId.add(R.drawable.myphoto1);
                TalentNetworkFragment talentNetworkFragment = new TalentNetworkFragment(talents, imagesId);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide).replace(R.id.accueilLineaireLayout, talentNetworkFragment).addToBackStack(null).commit();
            }
        });

        userName = (TextView) view.findViewById(R.id.userName);
        jobTitle = (TextView) view.findViewById(R.id.jobTitle);
        sectorLinearLayout = (LinearLayout) view.findViewById(R.id.sectorLinearLayout);
        companyLinearLayout = (LinearLayout) view.findViewById(R.id.companyLinearLayout);
        coutryLinearLayout = (LinearLayout) view.findViewById(R.id.coutryLinearLayout);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        jobTitle.setText(user.getJobTitle());
        //      final ExitFragmentTransition exitFragmentTransition = FragmentTransition.with(this).to(view.findViewById(R.id.profilImageview)).start(savedInstanceState);
        //      exitFragmentTransition.startExitListening();
        YoYo.with(Techniques.FadeInLeft)
                .duration(1400)
                .repeat(1)
                .playOn(view.findViewById(R.id.sectorLinearLayout));
        YoYo.with(Techniques.FadeInLeft)
                .duration(1400)
                .repeat(1)
                .playOn(view.findViewById(R.id.companyLinearLayout));
        YoYo.with(Techniques.FadeInLeft)
                .duration(1400)
                .repeat(1)
                .playOn(view.findViewById(R.id.coutryLinearLayout));
        return view;
    }


    private void moveViewToScreenCenter(final View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);
        int xDelta = (dm.widthPixels - 270) / 2;
        //  int yDelta = (dm.heightPixels - view.getMeasuredHeight() - originalPos[1])/2;

        int yDelta = (dm.heightPixels - originalPos[1]) / 2;

        AnimationSet animSet = new AnimationSet(false);
        animSet.setFillAfter(true);
        animSet.setDuration(1000);
        animSet.setInterpolator(new BounceInterpolator());
        TranslateAnimation translate = new TranslateAnimation(0, xDelta, 0, 450);
        animSet.addAnimation(translate);
        ScaleAnimation scale = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_PARENT, .5f, ScaleAnimation.RELATIVE_TO_PARENT, .5f);
        animSet.addAnimation(scale);
        view.startAnimation(animSet);
    }

}
