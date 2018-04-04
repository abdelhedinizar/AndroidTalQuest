package com.forsyslab.talquest10;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.facebook.login.LoginManager;
import com.forsyslab.talquest10.adapter.Helper.GlideHelper;
import com.forsyslab.talquest10.adapter.HomeAdapter;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetAllProfilWithoutImage;
import com.forsyslab.talquest10.dagger.GetJobLeads;
import com.forsyslab.talquest10.fragments.ChangePasswordFragment;
import com.forsyslab.talquest10.fragments.profils.FriendProfilDescription;
import com.forsyslab.talquest10.fragments.profils.ProfilFragment;
import com.forsyslab.talquest10.fragments.TalentFragment;
import com.forsyslab.talquest10.fragments.TimeLineFragment;

import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.MenuItem;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.ImageService;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.IS_USER_CONNECTED;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.PROFIL;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

public class AccueilActivity extends AppCompatActivity {

    ArrayList<MenuItem> menu = new ArrayList<>();
    @BindView(R.id.bmb)
    BoomMenuButton boomMenuButton;

    Drawer result;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.accueilLineaireLayout)
    LinearLayout layout;
    ArrayAdapter<String> adapter;

    @BindView(R.id.touch_interceptor_view)
    View listTouchInterceptor;

    @BindView(R.id.details_layout)
    View detailsLayout;

    @BindView(R.id.unfoldable_view)
    UnfoldableView unfoldableView;

    @BindView(R.id.toolbar_container)
    FrameLayout frameLayout;

    @BindView(R.id.list_view)
    ListView listView;

    @Inject
    Retrofit retrofit;

    User userProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ButterKnife.bind(this);

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        Gson gson = new Gson();
        userProfil = gson.fromJson(PreferencesStorageService.getDefaults(PROFIL, getApplicationContext()), User.class);

        int blue = ContextCompat.getColor(this, R.color.blue);
        searchViewInstallation(toolbar);
        createNavigationDrawer(blue);
        final Call<List<JobLeads>> jobLeadsCall = retrofit.create(GetJobLeads.class).getJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
jobLeadsCall.enqueue(new Callback<List<JobLeads>>() {
    @Override
    public void success(Result<List<JobLeads>> result) {
        listView.setAdapter(new HomeAdapter(getBaseContext(),result.data));
    }

    @Override
    public void failure(TwitterException exception) {

        Gson gson = new Gson();
        String jobLeadsListGetString = PreferencesStorageService.getDefaults(JOB_LEADS, getApplicationContext());
        Type type = new TypeToken<List<JobLeads>>() {
        }.getType();
        List<JobLeads> jobs = gson.fromJson(jobLeadsListGetString, type);
        if(jobs!=null){
            listView.setAdapter(new HomeAdapter(getBaseContext(),jobs));
            Log.d("nizarab",jobs.toString());
        }


    }
});


        layout.setVisibility(View.GONE);
/*
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
        transaction.commit();
*/
        detailsLayout.setVisibility(View.INVISIBLE);
        listTouchInterceptor.setClickable(false);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.logo_2);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });

        getMenu(this);
        int jaune = ContextCompat.getColor(getApplicationContext(), R.color.jaune);
        boomMenuButton.setButtonEnum(ButtonEnum.Ham);
        if (menu.size() == 4) {
            boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
            boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        } else if (menu.size() == 5) {
            boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
            boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        }
        boomMenuButton.setBoomEnum(BoomEnum.PARABOLA_4);

        for (int i = 0; i < boomMenuButton.getButtonPlaceEnum().buttonNumber(); i++) {

            boomMenuButton.addBuilder(new HamButton.Builder().normalImageRes(menu.get(i).getMenuImageID()).normalText(menu.get(i).getMenuTitle()).subNormalText(menu.get(i).getMenuDescription())
                    .normalColor(blue)
                    .highlightedColor(jaune)
                    .pieceColor(jaune)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            if (index == 0) {
                                //          layout.setVisibility(View.GONE);
                                //           frameLayout.setVisibility(View.VISIBLE);
                                startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                            }
                            if (index == 1) {
                                ProfilFragment profilFragment = new ProfilFragment();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, profilFragment);
                                transaction.commit();
                                layout.setVisibility(View.VISIBLE);
                            }
                            if (index == 2) {
                                TalentFragment talentFragment = new TalentFragment();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, talentFragment);
                                transaction.commit();
                                layout.setVisibility(View.VISIBLE);
                                frameLayout.setVisibility(View.VISIBLE);
                            }
                            if (index == 3) {
                                TimeLineFragment timeLineFragment = new TimeLineFragment();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
                                transaction.commit();
                                frameLayout.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.VISIBLE);
                            }
                        }
                    }));
        }

    }

    private void createNavigationDrawer(int blue) {

        ProfileDrawerItem profileDrawerItem;

        if(userProfil.getLogo()!=null)
        {
            byte[] imageAsBytes;
            imageAsBytes = Base64.decode(userProfil.getLogo().getBytes(), Base64.DEFAULT);
            profileDrawerItem = new ProfileDrawerItem().withName(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, this)).
                    withIcon(ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
        }
        else
        {
            profileDrawerItem = new ProfileDrawerItem().withName(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, this)).
                    withIcon(R.drawable.ic_personimage);
        }

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.navigation_background)
                .addProfiles(
               profileDrawerItem
                ).withTextColor(ContextCompat.getColor(this, R.color.dark))

                .withHeightDp(200)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .withAccountHeader(R.layout.drawer_layout)
                .build();
        PrimaryDrawerItem item10;
        if(userProfil.getSolde()==null)
        {
             item10 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.ic_attach_money).withName("TalQuest Credit").withDescription("00.00"+"$").withTextColor(blue).withLevel(1).withSelectedTextColor(blue);
        }
        else{
             item10 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.ic_attach_money).withName("TalQuest Credit").withDescription(userProfil.getSolde()+"$").withTextColor(blue).withLevel(1).withSelectedTextColor(blue);
        }
        PrimaryDrawerItem item11 = new PrimaryDrawerItem().withIdentifier(2).withIcon(R.drawable.ic_person).withName("Account").withTextColor(blue).withLevel(1).withSelectedTextColor(blue);
        PrimaryDrawerItem item12 = new PrimaryDrawerItem().withIdentifier(3).withIcon(R.drawable.ic_vpn_key_gris).withName("Change password").withTextColor(blue).withLevel(1).withSelectedTextColor(blue);

        SecondaryDrawerItem item20 = new SecondaryDrawerItem().withIdentifier(4).withName("settings").withLevel(1);
        SecondaryDrawerItem item21 = new SecondaryDrawerItem().withIdentifier(4).withName("about").withLevel(1);

//create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(false)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item10, item11, item12,
                        new DividerDrawerItem(),
                        item20, item21,
                        new SecondaryDrawerItem().withName("sign out").withLevel(1)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (position == 2) {
                            layout.setVisibility(View.VISIBLE);
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            TimeLineFragment timeLineFragment = new TimeLineFragment();
                            transaction.addToBackStack("");
                            transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
                            transaction.commit();
                        } else if (position == 3) {
                            layout.setVisibility(View.VISIBLE);
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                            transaction.addToBackStack("");
                            transaction.replace(R.id.accueilLineaireLayout, changePasswordFragment);
                            transaction.commit();
                        } else if (position == 7) {
                            PreferencesStorageService.setDefaults(IS_USER_CONNECTED, Boolean.toString(false), getApplicationContext());
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }

                        result.closeDrawer();
                        return true;
                    }
                })
                .build();
    }


    public void getMenu(Context context) {
        int[] menuImageID = {R.drawable.accueil, R.drawable.profil, R.drawable.add_recognitions, R.drawable.notification, R.drawable.notification};
        String[] menuTitle = context.getResources().getStringArray(R.array.accueilmenuPersonalProfil);
        String[] menuDescription = context.getResources().getStringArray(R.array.accueilMenuPersonalProfilInformation);
        for (int i = 0; i < menuTitle.length; i++) {
            menu.add(new MenuItem(menuImageID[i], menuTitle[i], menuDescription[i]));
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(this, AccueilActivity.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.VISIBLE);
        if (unfoldableView != null && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
            unfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, JobLeads jobLeads, int drawableId) {
        final ImageView image = (ImageView) detailsLayout.findViewById(R.id.details_image);
        final TextView title = (TextView) detailsLayout.findViewById(R.id.details_title);
        final TextView description = (TextView) detailsLayout.findViewById(R.id.details_text);

        GlideHelper.loadPaintingImage(image, drawableId);
        title.setText(jobLeads.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("title").append(": ")
                .clearStyle()
                .append(jobLeads.getTitle()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("salary").append(": ")
                .clearStyle()
                .append(jobLeads.getSalary()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("Description du post").append(": ").append("\n\n").clearStyle()
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(jobLeads.getCompanyDesc())
                .append("Mission/Activities Principales").append(": ").append("\n\n").clearStyle()
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("profil").append(": ").append("\n\n").clearStyle()
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("Formation").append(": ").append("\n\n").clearStyle()
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append("experience").append(": ").append("\n\n").clearStyle();
        description.setText(builder.build());

        unfoldableView.unfold(coverView, detailsLayout);
    }


    private void searchViewInstallation(Toolbar toolbar) {

        setSupportActionBar(toolbar);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                final Call<List<UserDto>> users = retrofit.create(GetAllProfilWithoutImage.class).getUsers(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
                users.enqueue(new Callback<List<UserDto>>() {
                                  @Override
                                  public void success(final Result<List<UserDto>> result) {
                                      ArrayList<String> usersName = new ArrayList<>();
                                      final ArrayList<UserDto> userDtos = new ArrayList<>();
                                      for (int i = 0; i < result.data.size(); i++) {

                                          if (null != result.data.get(i).getType()) {
                                              if (result.data.get(i).getType().equals(USER_KEY)) {
                                                  usersName.add(result.data.get(i).getFirstName() + " " + result.data.get(i).getLastName());
                                                  userDtos.add(result.data.get(i));
                                              }
                                          }
                                      }
                                      adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, usersName);
                                      searchView.setAdapter(adapter);
                                      searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              for(UserDto userDto:userDtos)
                                              {
                                                  if((userDto.getFirstName()+" "+userDto.getLastName()).equals(parent.getAdapter().getItem(position).toString()))
                                                  {
                                                      FragmentManager manager = getSupportFragmentManager();
                                                      FragmentTransaction transaction = manager.beginTransaction();
                                                      transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                                                      FriendProfilDescription friendProfilDescription = new FriendProfilDescription(userDto.changeToUser());
                                                      transaction.addToBackStack("");
                                                      transaction.replace(R.id.accueilLineaireLayout, friendProfilDescription);
                                                      transaction.commit();
                                                      layout.setVisibility(View.VISIBLE);
                                                  }
                                              }

                                          }
                                      });
                                  }

                                  @Override
                                  public void failure(TwitterException exception) {

                                  }
                              }
                );
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem menuItem) {
                if ("settings".equals(menuItem.getTitle()))
                    Toast.makeText(getApplicationContext(), menuItem.getTitle().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        android.view.MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;

    }
}
