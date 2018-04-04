package com.forsyslab.talquest10.fragments.signUp.npo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.forsyslab.talquest10.MainActivity;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.Register;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.COMPANY_KEY;
import static com.forsyslab.talquest10.constant.Const.NGO_KEY;

/**
 * Created by LENOVO on 28/01/2017.
 */

public class SignUpNGOStep4 extends Fragment {

    private UserDto ngo;

    @Inject
    Retrofit retrofit;

    EditText signUpWebSite;
    EditText signUpFacebookAccount;
    EditText signUpTwitterAccount;
    EditText signUpGoogleAccount;
    EditText signUpLinkedinAccount;
    FloatingActionButton button;

    public SignUpNGOStep4(UserDto ngo) {
        this.ngo = ngo;
    }
    public SignUpNGOStep4() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ngo_step4_sign_up, container, false);
        signUpWebSite = (EditText) view.findViewById(R.id.signUpWebSite);
        signUpFacebookAccount = (EditText) view.findViewById(R.id.signUpFacebookAccount);
        signUpTwitterAccount = (EditText) view.findViewById(R.id.signUpTwitterAccount);
        signUpGoogleAccount = (EditText) view.findViewById(R.id.signUpGoogleAccount);
        signUpLinkedinAccount = (EditText) view.findViewById(R.id.signUpLinkedinAccount);
        button = (FloatingActionButton) view.findViewById(R.id.step4Button);
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngo.setWebsite(signUpWebSite.getText().toString());
                ngo.setFbAccount(signUpFacebookAccount.getText().toString());
                ngo.setGoogleAccount(signUpGoogleAccount.getText().toString());
                ngo.setLinkedinAccount(signUpLinkedinAccount.getText().toString());
                ngo.setTwitterAccount(signUpTwitterAccount.getText().toString());
                ngo.setType(NGO_KEY);
                Call<ResponseBody> registerCall = retrofit.create(Register.class).register(ngo);
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


        return view;
    }
}
