package com.forsyslab.talquest10.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.adapter.TalentAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetAllProfil;
import com.forsyslab.talquest10.dagger.GetAllProfilWithoutImage;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.GridSpacingItemDecoration;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.view.BezelImageView;
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
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.TAG;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

/**
 * Created by LENOVO on 31/03/2017.
 */

public class TalentFragment extends Fragment {


    @Inject
    Retrofit retrofit;


    private RecyclerView recyclerView;
    private TalentAdapter talentAdapter;
    private List<User> talents = new ArrayList<>();
    private List<Integer> imagesId = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talent, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        final Call<List<User>> talent = retrofit.create(GetAllProfil.class).getUsers(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        final Call<List<UserDto>> talentwithoutimages = retrofit.create(GetAllProfilWithoutImage.class).getUsers(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true,getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        talentwithoutimages.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void success(Result<List<UserDto>> result) {
                talents.clear();
                for (UserDto user : result.data) {
                    if (user.getType() != null) {
                        if (!user.getLogin().equals(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, getApplicationContext())) && user.getType().equals(USER_KEY)) {
                            talents.add(user.changeToUser());
                        }
                    }
                }
                talentAdapter = new TalentAdapter(getContext(), getActivity());
                talentAdapter.setUserList(talents);
                recyclerView.setAdapter(talentAdapter);


            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
        talent.enqueue(new Callback<List<User>>() {
            @Override
            public void success(Result<List<User>> result) {
                talents.clear();
                for (User user : result.data) {
                    if (user.getType() != null) {
                        if (!user.getLogin().equals(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, getApplicationContext())) && user.getType().equals(USER_KEY)) {
                            talents.add(user);
                        }
                    }
                }
                imagesId.add(R.drawable.guillaume_fric);
                imagesId.add(R.drawable.seif);
                imagesId.add(R.drawable.clement_kopp);
                imagesId.add(R.drawable.hugues_seureau);
                imagesId.add(R.drawable.safar_jean);
                imagesId.add(R.drawable.safar_jean);
                talentAdapter = new TalentAdapter(getContext(), getActivity());
                talentAdapter.setUserList(talents);
                talentAdapter.setImageId(imagesId);
                recyclerView.setAdapter(talentAdapter);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, exception.toString());
            }
        });

        /*
        talents.clear();
        talents.add(new User("Guillaume", "fric", "Directeur GlobaCOS France"));
        talents.add(new User("Seif", "Mouelhi", "Regional Manager"));
        talents.add(new User("Clement", "Kopp", "Talent Search & Transformation"));
        talents.add(new User("Hugues", "seureau", "Market Analyst Specialist"));
        talents.add(new User("Jean", "safar", "Chief Architect"));

*/




      /*  imageView = (BezelImageView) view.findViewById(R.id.material_drawer_account_header_current);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendProfilDescription profilDescription = new FriendProfilDescription();
                FragmentTransitionLauncher
                        .with(view.getContext())
                        .image(BitmapFactory.decodeResource(getResources(), R.drawable.seif))
                        .from(view.findViewById(R.id.material_drawer_account_header_current)).prepare(profilDescription);
                getFragmentManager().beginTransaction().replace(R.id.accueilLineaireLayout, profilDescription).addToBackStack(null).commit();
            }
        });
*/

        return view;
    }

}
