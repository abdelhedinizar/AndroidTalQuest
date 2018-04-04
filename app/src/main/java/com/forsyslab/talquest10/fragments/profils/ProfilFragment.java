package com.forsyslab.talquest10.fragments.profils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.PutProfil;
import com.forsyslab.talquest10.model.PersonalProfil;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.network.VolleyParce;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.ApiAdresse.PROFILS_API;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.JSON_EMAIL;
import static com.forsyslab.talquest10.constant.Const.JSON_FIRST_NAME;
import static com.forsyslab.talquest10.constant.Const.JSON_LAST_NAME;
import static com.forsyslab.talquest10.constant.Const.JSON_TYPE;
import static com.forsyslab.talquest10.constant.Const.JSON_USERNAME;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.PROFIL;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;
import static com.forsyslab.talquest10.services.UserInputVerification.showTextViewComparedToLenght;

/**
 * Created by Nizar on 01/02/2017.
 */

public class ProfilFragment extends Fragment {

    private static final int REQUEST_CODE_DOC = 3;
    @Inject
    Retrofit retrofit;

    @BindView(R.id.MyToolbar)
    Toolbar toolbar;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.MyAppbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.userImage)
    BezelImageView userImage;
    @BindView(R.id.countryProfil)
    TextView countryProfil;
    @BindView(R.id.postCodeProfil)
    TextView postCodeProfil;
    @BindView(R.id.jobTitleProfil)
    TextView jobTitleProfil;
    @BindView(R.id.sectorProfil)
    TextView sectorProfil;
    @BindView(R.id.companyProfil)
    TextView companyProfil;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.countryEdit)
    ImageView countryEdit;
    @BindView(R.id.postCodeEdit)
    ImageView postCodeEdit;
    @BindView(R.id.jobTitleEdit)
    ImageView jobTitleEdit;
    @BindView(R.id.sectorEdit)
    ImageView sectorEdit;
    @BindView(R.id.companyEdit)
    ImageView companyEdit;
    @BindView(R.id.skillsEdit)
    ImageView skillsEdit;
    @BindView(R.id.experienceEdit)
    ImageView experienceEdit;
    @BindView(R.id.skillsEditText)
    AutoCompleteTextView skillsAutoCompleteText;
    @BindView(R.id.experienceProfil)
    TextView experienceProfil;

    String userLogin;
    User userProfil;

    @BindView(R.id.label_view)
    AutoLabelUI mAutoLabel;
    FrameLayout frameLayout;

    @BindView(R.id.addresume)
    Button addresume;

    PersonalProfil profil = new PersonalProfil();

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, view);
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.toolbar_container);
        frameLayout.setVisibility(View.GONE);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED);
        userLogin = PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, getActivity());
        collapsingToolbarLayout.setTitle(userLogin);

        nedScrollViewCorrection(view);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getActivity(), "menu cliked", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        String[] sc = getResources().getStringArray(R.array.skills);

        Gson gson = new Gson();
        userProfil = gson.fromJson(PreferencesStorageService.getDefaults(PROFIL, getApplicationContext()), User.class);

        if (userProfil.getSkills() != null) {
            Iterator<String> it = userProfil.getSkills().iterator();
            while (it.hasNext()) {
                mAutoLabel.addLabel(it.next());
            }
        }
        mAutoLabel.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {
                Toast.makeText(getContext(), ((Label) view).getText(), Toast.LENGTH_LONG).show();
            }
        });


        addresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("*/*");
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("*/*");
                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                startActivityForResult(chooserIntent, REQUEST_CODE_DOC);
            }
        });

        skillsAutoCompleteText.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, sc));

        skillsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (skillsAutoCompleteText.getVisibility() == View.INVISIBLE) {
                    skillsAutoCompleteText.setVisibility(View.VISIBLE);
                    skillsEdit.setImageResource(R.drawable.ic_add);
                } else if (skillsAutoCompleteText.getVisibility() == View.VISIBLE) {
                    skillsAutoCompleteText.setVisibility(View.INVISIBLE);
                    skillsEdit.setImageResource(android.R.drawable.ic_menu_edit);
                    mAutoLabel.addLabel(skillsAutoCompleteText.getText().toString());
                    if (!skillsAutoCompleteText.getText().toString().equals("")) {
                        Set<String> skills = new HashSet<>();
                        for (Label label : mAutoLabel.getLabels()) {
                            skills.add(label.getText().toString());
                        }
                        userProfil.setSkills(skills);
                        Call<User> profilCall = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), userProfil);

                        profilCall.enqueue(new Callback<User>() {
                            @Override
                            public void success(Result<User> result) {
                                Gson gs = new Gson();
                                PreferencesStorageService.setDefaults(PROFIL, gs.toJson(result.data), getApplicationContext());
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                Log.d("nizarab", exception.toString());
                            }
                        });
                    }
                    nedScrollViewCorrection(view);
                    skillsAutoCompleteText.setText("");
                }

            }
        });

        setProfilInformation();
        if (userProfil.getFirstName() != null && userProfil.getLastName() != null) {
            userName.setText(userProfil.getFirstName() + " " + userProfil.getLastName());
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getActivity(), R.color.jaune));


        //***********************************************Edit Profil************************************************************************
        // edit country listener
        countryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(getActivity())
                        .title("Edit country")
                        .items(R.array.countries)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
                                Toast.makeText(getActivity(), text.toString() + " " + which, Toast.LENGTH_LONG).show();
                                countryProfil.setText(text);
                                profil.setCountry(text.toString());
                                userProfil.setCountry(text.toString());

                                Call<User> profilCall = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), userProfil);

                                profilCall.enqueue(new Callback<User>() {
                                    @Override
                                    public void success(Result<User> result) {
                                        Log.d("nizarab","success");
                                        Gson gs = new Gson();
                                        PreferencesStorageService.setDefaults(PROFIL, gs.toJson(result.data), getApplicationContext());

                                    }

                                    @Override
                                    public void failure(TwitterException exception) {
                                        Log.d("nizarab","failed");

                                    }
                                });

                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });
        postCodeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Edit postCode")
                        .content("What's your postcode ?")
                        .inputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                                InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText("Ok")
                        .alwaysCallInputCallback() // this forces the callback to be invoked with every input change
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                postCodeProfil.setText(input);
                                profil.setPostcode(input.toString());
                                userProfil.setPostcode(input.toString());

                                Call<User> profilCall = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), userProfil);

                                profilCall.enqueue(new Callback<User>() {
                                    @Override
                                    public void success(Result<User> result) {
                                        Log.d("nizarab","success");
                                        Gson gs = new Gson();
                                        PreferencesStorageService.setDefaults(PROFIL, gs.toJson(result.data), getApplicationContext());

                                    }

                                    @Override
                                    public void failure(TwitterException exception) {
                                        Log.d("nizarab","failed");

                                    }
                                });


                            }
                        }).show();

            }
        });

        jobTitleEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Edit Job title")
                        .items(R.array.jobtitle)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
                                jobTitleProfil.setText(text);
                                profil.setJobTitle(text.toString());
                                return true;
                            }
                        }).positiveText(R.string.choose).show();
            }
        });

        sectorEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Edit sector")
                        .items(R.array.sector)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
                                sectorProfil.setText(text);
                                profil.setSector(text.toString());
                                showTextViewComparedToLenght(sectorProfil);
                                return true;
                            }
                        }).positiveText(R.string.choose).show();

            }
        });

        companyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(getActivity())
                        .title("Edit your Company")
                        .content("What's your company")
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                companyProfil.setText(input);
                                profil.setCompany(input.toString());
                            }
                        }).show();
            }
        });
        experienceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("Edit your Experience")
                        .content("What's your Experience")
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                experienceProfil.setText(input);
                                userProfil.setExperience(input.toString());
                                Call<User> profilPut = retrofit.create(PutProfil.class).putProfil(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())),userProfil);
                                profilPut.enqueue(new Callback<User>() {
                                    @Override
                                    public void success(Result<User> result) {
                                        Log.d("nizarab","succes");
                                        Gson gson = new Gson();
                                        PreferencesStorageService.setDefaults(PROFIL,gson.toJson(result.data),getApplicationContext());

                                    }
                                    @Override
                                    public void failure(TwitterException exception) {

                                    }
                                });
                            }
                        }).show();
            }
        });

//***************************************************************************************************************************
        return view;
    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("*/*");      //all files
        intent.setType("*/*");   //XML file only
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_CODE_DOC);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }


    private void setProfilInformation() {
        if (userProfil.getLogo() != null) {
            byte[] imageAsBytes = Base64.decode(userProfil.getLogo().getBytes(), Base64.DEFAULT);
            userImage.setImageBitmap(
                    resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
            );
        }
        if (userProfil.getCountry().toString() != null) {
            countryProfil.setText(userProfil.getCountry());
        }
        if (userProfil.getExperience() != null) {
            experienceProfil.setText(userProfil.getExperience());
        }
        if (userProfil.getPostcode() != null) {
            postCodeProfil.setText(userProfil.getPostcode());
        }
        if (userProfil.getJobTitle() != null) {
            jobTitleProfil.setText(userProfil.getJobTitle());
        }
        if (userProfil.getSector() != null) {
            sectorProfil.setText(userProfil.getSector());
            showTextViewComparedToLenght(sectorProfil);
        }
        if (userProfil.getCompany() != null) {
            companyProfil.setText(userProfil.getCompany());
        }
    }

    private Bitmap resize(Bitmap bitmap) {
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
        return bitmapResized;
    }


    private void nedScrollViewCorrection(View view) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        view.setMinimumHeight(screenHeight - actionBarHeight);
    }

    private void paramsDataInjection(JSONObject jsonObject) {

        try {
            jsonObject.put(JSON_USERNAME, "companyName");
            jsonObject.put(JSON_FIRST_NAME, "abdelhedi");
            jsonObject.put(JSON_LAST_NAME, "Nizar");
            jsonObject.put(JSON_EMAIL, "companyName@forsys.com");
            String[] authorities = {"ROLE_USER"};
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(authorities);
            jsonObject.put("authorities", jsonArray);
            jsonObject.put("activated", true);
            jsonObject.put(JSON_TYPE, USER_KEY);
            jsonObject.put("id", "589493b3d1df5614483fb540");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("companyName", "pause");

        JSONObject jsonObj = new JSONObject();
        paramsDataInjection(jsonObj);


        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, PROFILS_API, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("companyName", "response true");
                try {
                    response.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("companyName", "response failed");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String key = "Authorization";
                String value = "Bearer ".concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getActivity()));
                Map<String, String> map = new HashMap<String, String>();
                map.put(key, value);
                return map;
            }
        };

        VolleyParce.getInstance(getActivity()).add(objectRequest);

    }
/*
    public class DownloadImage implements Runnable{

        Bitmap myBitmap = null;

        @Override
        public void run() {

                    String imageURL = "https://fb-s-d-a.akamaihd.net/h-ak-fbx/v/t1.0-1/s200x200/18157222_10155148381663070_7102695784498464931_n.jpg?oh=6b6af4a2babc4a36f58cac47292092a4&oe=59CCF62D&__gda__=1510423129_074e85f09a0d8da8e1ccd23812ec0e29";
            HttpURLConnection conn = null;
            InputStream is = null;
                    try {
                        URL imageUrl = new URL(imageURL);
                        conn = (HttpURLConnection) imageUrl.openConnection();
                        is = conn.getInputStream();

                        conn.setInstanceFollowRedirects(true);
                        myBitmap = BitmapFactory.decodeStream(is);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    finally {
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           userImage.setImageBitmap(myBitmap);
                       }
                   });
                    }
                    if(conn!=null)
                    {
                        conn.disconnect();
                    }
                    if(is!=null)
                    {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE_DOC && resultCode == RESULT_OK
                    && null != data) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
