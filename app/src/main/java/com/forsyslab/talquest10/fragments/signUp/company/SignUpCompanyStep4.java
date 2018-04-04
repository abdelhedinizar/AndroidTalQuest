package com.forsyslab.talquest10.fragments.signUp.company;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.forsyslab.talquest10.MainActivity;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.constant.Const;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.Register;
import com.forsyslab.talquest10.model.Company;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.model.SuccessMessage;
import com.forsyslab.talquest10.model.User;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.COMPANY_KEY;
import static com.forsyslab.talquest10.services.UserInputVerification.isWebsiteValid;

/**
 * Created by Nizar Abdelhedi on 02/02/2017.
 */


public class SignUpCompanyStep4 extends Fragment implements android.text.TextWatcher {

    private UserDto company;

    EditText signUpWebSite;
    EditText signUpFacebookAccount;
    EditText signUpTwitterAccount;
    EditText signUpGoogleAccount;
    EditText signUpLinkedinAccount;
    FloatingActionButton button;

    @Inject
    Retrofit retrofit;

    public SignUpCompanyStep4(UserDto company) {
        this.company = company;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_step4_sign_up, container, false);
        signUpWebSite = (EditText) view.findViewById(R.id.signUpWebSite);
        signUpFacebookAccount = (EditText) view.findViewById(R.id.signUpFacebookAccount);
        signUpTwitterAccount = (EditText) view.findViewById(R.id.signUpTwitterAccount);
        signUpGoogleAccount = (EditText) view.findViewById(R.id.signUpGoogleAccount);
        signUpLinkedinAccount = (EditText) view.findViewById(R.id.signUpLinkedinAccount);
        button = (FloatingActionButton) view.findViewById(R.id.step4Button);

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                company.setWebsite(signUpWebSite.getText().toString());
                company.setFbAccount(signUpFacebookAccount.getText().toString());
                company.setGoogleAccount(signUpGoogleAccount.getText().toString());
                company.setLinkedinAccount(signUpLinkedinAccount.getText().toString());
                company.setTwitterAccount(signUpTwitterAccount.getText().toString());
                company.setType(COMPANY_KEY);
                Gson gson = new Gson();
                Log.d("nizarRegister",gson.toJson(company));
                Call<ResponseBody> registerCall = retrofit.create(Register.class).register(company);
                registerCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void success(Result<ResponseBody> result) {
                        Toast.makeText(getApplicationContext(), "succes", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                    @Override
                    public void failure(TwitterException exception) {
                        new MaterialDialog.Builder(getActivity()).title("please try again").content("login or email already in use").positiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }).show();

                    }
                });


            }
        });

        signUpWebSite.addTextChangedListener(this);
        signUpGoogleAccount.addTextChangedListener(this);
        signUpLinkedinAccount.addTextChangedListener(this);
        signUpFacebookAccount.addTextChangedListener(this);
        signUpTwitterAccount.addTextChangedListener(this);
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if ((!signUpWebSite.getText().toString().equals("")) && (!isWebsiteValid(signUpWebSite.getText().toString()))) {
            button.setEnabled(false);
            signUpWebSite.setError("Please write your real site");
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if ((!signUpFacebookAccount.getText().toString().equals("")) && (!isWebsiteValid(signUpFacebookAccount.getText().toString()))) {
            signUpFacebookAccount.setError("Please write your real facebook account");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if ((!signUpTwitterAccount.getText().toString().equals("")) && (!isWebsiteValid(signUpTwitterAccount.getText().toString()))) {
            signUpTwitterAccount.setError("Please write your real Twitter account");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if ((!signUpGoogleAccount.getText().toString().equals("")) && (!isWebsiteValid(signUpGoogleAccount.getText().toString()))) {
            signUpGoogleAccount.setError("Please write your real google account");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));

        } else if ((!signUpLinkedinAccount.getText().toString().equals("")) && (!isWebsiteValid(signUpLinkedinAccount.getText().toString()))) {
            signUpLinkedinAccount.setError("Please write your real linkedin account");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));

        } else {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }


    }
}
