package com.forsyslab.talquest10.fragments.signUp.personalUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.forsyslab.talquest10.WelcomeActivity;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.Authentification;
import com.forsyslab.talquest10.dagger.Register;
import com.forsyslab.talquest10.model.Authenticate;
import com.forsyslab.talquest10.model.IdToken;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.JSON_PASSWORD;
import static com.forsyslab.talquest10.constant.Const.JSON_REMEMBER_ME;
import static com.forsyslab.talquest10.constant.Const.JSON_USERNAME;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;

/**
 * Created by companyName on 19/01/2017.
 */

public class SignUpPersonalUserStep4 extends Fragment {


    @Inject
    Retrofit retrofit;


    UserDto profil;
    EditText company;
    MaterialSpinner jobTitle;
    MaterialSpinner sector;
    FloatingActionButton button;

    public SignUpPersonalUserStep4(UserDto profil) {
        this.profil = profil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step4_sign_up, container, false);
        jobTitle = (MaterialSpinner) view.findViewById(R.id.signUpJobTitle);
        jobTitle.setItems(getResources().getStringArray(R.array.jobtitle));
        company = (EditText) view.findViewById(R.id.signUpCompany);
        sector = (MaterialSpinner) view.findViewById(R.id.signUpSector);
        sector.setItems(getResources().getStringArray(R.array.sector));

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);


        button = (FloatingActionButton) view.findViewById(R.id.step5Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profil.setJobTitle(jobTitle.getText().toString());
                profil.setCompany(company.getText().toString());
                profil.setSector(sector.getText().toString());
                final Call<ResponseBody> register = retrofit.create(Register.class).register(profil);


                register.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void success(Result<ResponseBody> result) {
                        Toast.makeText(getApplicationContext(), "succes", Toast.LENGTH_LONG).show();
//                        Gson gs = new Gson();
//                        Log.d("nizarab",gs.toJson(profil));
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

/*                Map<String, String> params = new HashMap<String, String>();
                params.put(JSON_EMAIL, user.getEmail());
                params.put(JSON_FIRST_NAME, user.getFirstName());
                params.put(JSON_LAST_NAME, user.getLastName());
                params.put(JSON_LOGIN, user.getLogin());
                params.put(JSON_PASSWORD, user.getPassword());
                params.put(JSON_COUNTRY, user.getCountry());
                params.put(JSON_POSTCODE, user.getPostcode());
                params.put(JSON_JOB_TITLE, user.getJobTitle());
                params.put(JSON_COMPANY, user.getCompany());
                params.put(JSON_SECTOR, user.getSector());
                params.put(JSON_TYPE, "user");

                Map<String, String> loginParams = new HashMap<String, String>();
                paramsDataInjection(loginParams);
                final JSONObject loginJsonObj = new JSONObject(loginParams);

                final JsonObjectRequest loginJsonRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_API, loginJsonObj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String id_token = null;
                        try {
                            id_token = response.getString("id_token");
                            Log.d("id_token", id_token);
                            PreferencesStorageService.setDefaults(PREFERENCES_TOKEN_KEY, id_token, getActivity());
                            startActivity(new Intent(getActivity(), WelcomeActivity.class));
                            getActivity().finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //     Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //   startActivity(intent); //just for test Accuiel
                        //   finish();
                        Log.d("error", "error");
                    }
                });
                JSONObject jsonObj = new JSONObject(params);

                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, SIGN_UP_API, jsonObj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(JSON_SUCCESS).equals("true")) {
                                PreferencesStorageService.setDefaults(PREFERENCES_LOGIN, user.getLogin(), getActivity());
                                PreferencesStorageService.setDefaults(JSON_PASSWORD, user.getPassword(), getActivity());
                                VolleyParce.getInstance(getActivity()).add(loginJsonRequest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if (error.networkResponse.statusCode == 400) {
                            try {
                                JSONObject jsonObj = new JSONObject(new String(error.networkResponse.data));

                                new MaterialDialog.Builder(getActivity()).title("please try again").content(jsonObj.getString(JSON_SUCCESS)).positiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                    }
                                }).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                VolleyParce.getInstance(getActivity()).add(stringRequest);
*/


            }
        });


        return view;
    }
}
