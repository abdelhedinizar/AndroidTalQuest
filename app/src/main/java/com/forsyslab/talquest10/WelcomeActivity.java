package com.forsyslab.talquest10;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetProfil;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.COMPANY_KEY;
import static com.forsyslab.talquest10.constant.Const.NGO_KEY;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.PROFIL;
import static com.forsyslab.talquest10.constant.Const.TAG;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

public class WelcomeActivity extends AppCompatActivity {

    String userLogin;
    @Inject
    Retrofit retrofit;
    @BindView(R.id.numberProgressBar)
    NumberProgressBar numberProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userLogin = PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, this);

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        Call<User> profilCall = retrofit.create(GetProfil.class).getUser(userLogin, BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        profilCall.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {

                Gson gson = new Gson();
                String profilJson = gson.toJson(result.data);
                PreferencesStorageService.setDefaults(PROFIL, profilJson, getApplicationContext());

                if (result.data.getType().equals(USER_KEY)) {
                    startAccueilActivityByUserType(AccueilActivity.class);
                }
                if (result.data.getType().equals(COMPANY_KEY)) {
                    startAccueilActivityByUserType(AccueilCompanyActivity.class);
                }
                if (result.data.getType().equals(NGO_KEY)) {
                    startAccueilActivityByUserType(AccueilNGOActivity.class);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, exception.toString());
                startAccueilActivityByUserType(AccueilNGOActivity.class);
            }
        });
    }

    private void startAccueilActivityByUserType(final Class accueilClass) {
        for (int i = 0; i < 101; i++) {
            final int j = i;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    numberProgressBar.setProgress(j);
                    if (j == 99) {
                        startActivity(new Intent(getApplicationContext(), accueilClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }
            }, i * 10);
        }
    }


}
