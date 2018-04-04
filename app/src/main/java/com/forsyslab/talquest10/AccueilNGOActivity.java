package com.forsyslab.talquest10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.forsyslab.talquest10.fragments.ChangePasswordFragment;
import com.forsyslab.talquest10.fragments.PostJobLeadFragment;
import com.forsyslab.talquest10.fragments.profils.ProfilCompanyFragment;
import com.forsyslab.talquest10.fragments.TimeLineFragment;
import com.forsyslab.talquest10.model.MenuItem;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.services.ImageService;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forsyslab.talquest10.constant.Const.IS_USER_CONNECTED;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PROFIL;

public class AccueilNGOActivity extends AppCompatActivity {

    ArrayList<MenuItem> menu = new ArrayList<>();
    @BindView(R.id.bmb)
    BoomMenuButton boomMenuButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Drawer result;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar_container)
    FrameLayout frameLayout;

    User userProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_ngo);
        ButterKnife.bind(this);

        Gson gson = new Gson();
        userProfil = gson.fromJson(PreferencesStorageService.getDefaults(PROFIL, getApplicationContext()), User.class);


        searchViewInstallation(toolbar);

        int blue = ContextCompat.getColor(this, R.color.blue);
        createNavigationDrawer(blue);

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem menuItem) {
                if ("settings".equals(menuItem.getTitle()))
                    Toast.makeText(getApplicationContext(), menuItem.getTitle().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
        transaction.commit();

        getMenu(this);
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
                    .normalColor(ContextCompat.getColor(getApplication(), R.color.blue))
                    .highlightedColor(ContextCompat.getColor(getApplication(), R.color.jaune))
                    .pieceColor(ContextCompat.getColor(getApplication(), R.color.jaune))

                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            if (index == 0) {
                                TimeLineFragment timeLineFragment1 = new TimeLineFragment();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, timeLineFragment1);
                                transaction.commit();
                                frameLayout.setVisibility(View.VISIBLE);
                            }
                            if (index == 1) {
                                ProfilCompanyFragment profilFragment = new ProfilCompanyFragment(1);
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, profilFragment);
                                transaction.commit();
                                frameLayout.setVisibility(View.GONE);
                            }
                            if (index == 3) {
                                PostJobLeadFragment postJobLeadFragment = new PostJobLeadFragment();
                                transaction.addToBackStack("");
                                transaction.replace(R.id.accueilLineaireLayout, postJobLeadFragment);
                                transaction.commit();
                                frameLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }));
        }
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
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
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


    public void getMenu(Context context) {
        int[] menuImageID = {R.drawable.accueil, R.drawable.profil, R.drawable.add_recognitions, R.drawable.notification, R.drawable.notification};
        String[] menuTitle = context.getResources().getStringArray(R.array.accueilmenuNGO);
        String[] menuDescription = context.getResources().getStringArray(R.array.accueilMenuCompanyInformation);
        for (int i = 0; i < menuTitle.length; i++) {
            menu.add(new MenuItem(menuImageID[i], menuTitle[i], menuDescription[i]));
        }
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 500, 500, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


    private void createNavigationDrawer(int blue) {


        ProfileDrawerItem profileDrawerItem;


        if (userProfil.getLogo() != null) {
            byte[] imageAsBytes;
            imageAsBytes = Base64.decode(userProfil.getLogo().getBytes(), Base64.DEFAULT);
            profileDrawerItem = new ProfileDrawerItem().withName(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, this)).
                    withIcon(ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
        } else {
            profileDrawerItem = new ProfileDrawerItem().withName(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, this)).
                    withIcon(R.drawable.ic_personimage);
        }


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.navigation_background)
                .addProfiles(profileDrawerItem).withTextColor(ContextCompat.getColor(this, R.color.dark))

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


  //      PrimaryDrawerItem item10 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.ic_attach_money).withName("TalQuest Credit").withDescription("0.00$").withTextColor(blue).withLevel(1).withSelectedTextColor(blue);
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
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            TimeLineFragment timeLineFragment = new TimeLineFragment();
                            transaction.addToBackStack("");
                            transaction.replace(R.id.fragment_accueil_linearLayout, timeLineFragment);
                            transaction.commit();
                        } else if (position == 3) {
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                            transaction.addToBackStack("");
                            transaction.replace(R.id.fragment_accueil_linearLayout, changePasswordFragment);
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


}
