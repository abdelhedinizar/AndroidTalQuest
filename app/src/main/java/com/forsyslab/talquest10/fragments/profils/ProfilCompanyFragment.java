package com.forsyslab.talquest10.fragments.profils;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.view.BezelImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.PROFIL;
import static com.forsyslab.talquest10.services.ImageService.resize;

/**
 * Created by LENOVO on 10/02/2017.
 */

public class ProfilCompanyFragment extends Fragment {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ImageView creationDateEdit;

    int type;

    public ProfilCompanyFragment(int type)
    {
        this.type = type;
    }


    @BindView(R.id.bgheader)
    ImageView bgheader;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.descriptionProfil)
    TextView descriptionProfil;
    @BindView(R.id.creationDateProfil)
    TextView creationDateProfil;
    @BindView(R.id.countryProfil)
    TextView countryProfil;
    @BindView(R.id.postCodeProfil)
    TextView postCodeProfil;
    @BindView(R.id.webSiteProfil)
    TextView webSiteProfil;
    @BindView(R.id.facebookProfil)
    TextView facebookProfil;
    @BindView(R.id.googleProfil)
    TextView googleProfil;
    @BindView(R.id.twitterProfil)
    TextView twitterProfil;
    @BindView(R.id.linkedInProfil)
    TextView linkedInProfil;
    @BindView(R.id.myphoto)
    BezelImageView myphoto;
    @BindView(R.id.noEmp)
    TextView noEmp;

    FrameLayout frameLayout;

    User userProfil;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_company_profil, container, false);
        ButterKnife.bind(this, view);
        frameLayout =(FrameLayout) getActivity().findViewById(R.id.toolbar_container);
        frameLayout.setVisibility(View.GONE);
        Gson gson = new Gson();
        userProfil = gson.fromJson(PreferencesStorageService.getDefaults(PROFIL, getApplicationContext()), User.class);

        companyName.setText(userProfil.getLogin());
        descriptionProfil.setText(userProfil.getDescription());
        creationDateProfil.setText(userProfil.getCreatedDate());
        countryProfil.setText(userProfil.getCountry());
        postCodeProfil.setText(userProfil.getPostcode());
        webSiteProfil.setText(userProfil.getWebsite());
      //  facebookProfil.setText(userProfil.getFbAccount());
      //  googleProfil.setText(userProfil.getGoogleAccount());
      //  twitterProfil.setText(userProfil.getTwitterAccount());
     //   linkedInProfil.setText(userProfil.getLinkedinAccount());
     //   noEmp.setText(userProfil.getNoEmp());

        if(userProfil.getLogo()!=null)
        {
            byte[] imageAsBytes = Base64.decode(userProfil.getLogo().getBytes(), Base64.DEFAULT);
            myphoto.setImageBitmap(
                    resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
            );
        }


        findAllViews(view);
        return view;
    }

    private void findAllViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.MyToolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.MyAppbar);

        creationDateEdit = (ImageView) view.findViewById(R.id.creationDateEdit);

    }
}
