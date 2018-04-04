package com.forsyslab.talquest10.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.GetAllProfilWithoutImage;
import com.forsyslab.talquest10.dagger.PostJobLeads;
import com.forsyslab.talquest10.fragments.profils.FriendProfilDescription;
import com.forsyslab.talquest10.model.ModelDto.JobLeadsDto;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.forsyslab.talquest10.constant.Const.BEARER;
import static com.forsyslab.talquest10.constant.Const.NGO_KEY;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

/**
 * Created by LENOVO on 10/04/2017.
 */

public class PostJobLeadFragment extends Fragment {
    EditText jobTitleEditText;
    EditText missionDescriptionEditText;
    EditText companyNameEditText;
    EditText companyDescriptionEditText;
    EditText profilSearchEditText;
    EditText salaryEditText;
    EditText ngoEditText;
    TextView availability;
    DiscreteSeekBar seekBar;
    JobLeadsDto jobLead;
    DatePickerDialog dpd;
    Button postulate;
    Boolean[] verif = new Boolean[11];

    @Inject
    Retrofit retrofit;

    LinearLayout jobTitle;
    LinearLayout missionDescription;
    LinearLayout companyName;
    LinearLayout companyDescription;
    LinearLayout profilSearch;
    LinearLayout salary;
    LinearLayout ngo;

    Spinner npoSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_jobleads, container, false);

        for (int i = 0; i < verif.length; i++) {
            verif[i] = false;
        }

        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);
        jobLead = new JobLeadsDto();

        postulate = (Button) view.findViewById(R.id.postulate);

        final String[] mySteps = {"Job Title", "Mission description", "Sector", "Company name", "Company description", "Profil search",
                "experience", "availability", "salary", "NPO", "reward choosen"};
/*****************************************- job title -************************************************************************/
        jobTitle = (LinearLayout) view.findViewById(R.id.jobTitle);
        TextView jobTitleStep_title = (TextView) jobTitle.findViewById(R.id.step_title);
        jobTitleStep_title.setText(mySteps[0]);
        final RelativeLayout jobTitleStep_Header = (RelativeLayout) jobTitle.findViewById(R.id.step_header);
        jobTitleStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) jobTitle.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
        final TextView jobTitleStep_number = (TextView) jobTitle.findViewById(R.id.step_number);
        jobTitleStep_number.setText("1");
        jobTitleEditText = (EditText) jobTitle.findViewById(R.id.textInput);
        jobTitleEditText.setHint("write your job title");
        jobTitleEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        jobTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) jobTitle.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) jobTitle.findViewById(R.id.step_done);
                TextView jobTitleStep_number = (TextView) jobTitle.findViewById(R.id.step_number);
                if (charSequence.length() < 4) {
                    jobTitleEditText.setError("the title must have at least 4 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    jobTitleStep_number.setVisibility(View.VISIBLE);
                    verif[0] = false;
                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    jobTitleStep_number.setVisibility(View.INVISIBLE);
                    verif[0] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

/*****************************************- missionDescription -************************************************************************/

        missionDescription = (LinearLayout) view.findViewById(R.id.missionDescription);
        TextView missionDescriptionStep_title = (TextView) missionDescription.findViewById(R.id.step_title);
        missionDescriptionStep_title.setText(mySteps[1]);
        TextView missionDescriptionStep_number = (TextView) missionDescription.findViewById(R.id.step_number);
        missionDescriptionStep_number.setText("2");
        RelativeLayout missionDescriptionStep_Header = (RelativeLayout) missionDescription.findViewById(R.id.step_header);
        missionDescriptionStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) missionDescription.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });

        missionDescriptionEditText = (EditText) missionDescription.findViewById(R.id.textInput);
        missionDescriptionEditText.setHint("describe the job mission");
        missionDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) missionDescription.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) missionDescription.findViewById(R.id.step_done);
                TextView step_number = (TextView) missionDescription.findViewById(R.id.step_number);
                if (charSequence.length() < 40) {
                    missionDescriptionEditText.setError("the title must have at least 40 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    step_number.setVisibility(View.VISIBLE);
                    verif[1] = false;
                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                    verif[1] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

/*****************************************- company name -************************************************************************/
        companyName = (LinearLayout) view.findViewById(R.id.companyName);
        TextView companyNameStep_title = (TextView) companyName.findViewById(R.id.step_title);
        companyNameStep_title.setText(mySteps[3]);
        TextView companyNameStep_number = (TextView) companyName.findViewById(R.id.step_number);
        companyNameStep_number.setText("3");
        RelativeLayout companyNameStep_Header = (RelativeLayout) companyName.findViewById(R.id.step_header);
        companyNameStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) companyName.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });

        companyNameEditText = (EditText) companyName.findViewById(R.id.textInput);
        companyNameEditText.setHint(mySteps[3]);
        companyNameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        companyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) companyName.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) companyName.findViewById(R.id.step_done);
                TextView step_number = (TextView) companyName.findViewById(R.id.step_number);
                if (charSequence.length() < 4) {
                    companyNameEditText.setError("the title must have at least 4 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    step_number.setVisibility(View.VISIBLE);
                    verif[2] = false;
                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                    verif[2] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

/*****************************************- company Description -************************************************************************/

        companyDescription = (LinearLayout) view.findViewById(R.id.companyDescription);
        TextView companyDescriptionStep_title = (TextView) companyDescription.findViewById(R.id.step_title);
        companyDescriptionStep_title.setText(mySteps[4]);
        TextView companyDescriptionStep_number = (TextView) companyDescription.findViewById(R.id.step_number);
        companyDescriptionStep_number.setText("4");

        RelativeLayout companyDescriptionStep_Header = (RelativeLayout) companyDescription.findViewById(R.id.step_header);
        companyDescriptionStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) companyDescription.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
        companyDescriptionEditText = (EditText) companyDescription.findViewById(R.id.textInput);
        companyDescriptionEditText.setHint("describe your company");
        companyDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ImageView doneImage = (ImageView) companyDescription.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) companyDescription.findViewById(R.id.step_done);
                TextView step_number = (TextView) companyDescription.findViewById(R.id.step_number);
                if (charSequence.length() < 40) {
                    companyDescriptionEditText.setError("the title must have at least 40 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    step_number.setVisibility(View.VISIBLE);
                    verif[3] = false;
                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                    verif[3] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
/*****************************************- profil Search -************************************************************************/
        profilSearch = (LinearLayout) view.findViewById(R.id.profilSearch);
        TextView profilSearchStep_title = (TextView) profilSearch.findViewById(R.id.step_title);
        profilSearchStep_title.setText(mySteps[5]);
        TextView profilSearchStep_number = (TextView) profilSearch.findViewById(R.id.step_number);
        profilSearchStep_number.setText("6");
        RelativeLayout profilSearchStep_Header = (RelativeLayout) profilSearch.findViewById(R.id.step_header);
        profilSearchStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) profilSearch.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
        profilSearchEditText = (EditText) profilSearch.findViewById(R.id.textInput);
        profilSearchEditText.setHint(mySteps[5]);
        profilSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) profilSearch.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) profilSearch.findViewById(R.id.step_done);
                TextView step_number = (TextView) profilSearch.findViewById(R.id.step_number);

                if (charSequence.length() < 40) {
                    profilSearchEditText.setError("the title must have at least 40 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    step_number.setVisibility(View.VISIBLE);
                    verif[5] = false;

                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                    verif[5] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
/*****************************************- salary -************************************************************************/
        salary = (LinearLayout) view.findViewById(R.id.salary);
        TextView salaryStep_title = (TextView) salary.findViewById(R.id.step_title);
        salaryStep_title.setText(mySteps[8]);
        TextView salaryStep_number = (TextView) salary.findViewById(R.id.step_number);
        salaryStep_number.setText("8");
        RelativeLayout salaryStep_Header = (RelativeLayout) salary.findViewById(R.id.step_header);
        salaryStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) salary.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
        salaryEditText = (EditText) salary.findViewById(R.id.textInput);
        salaryEditText.setHint(mySteps[8]);
        salaryEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        salaryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) salary.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) salary.findViewById(R.id.step_done);
                TextView step_number = (TextView) salary.findViewById(R.id.step_number);
                try {
                    if (Integer.parseInt(charSequence.toString()) < 2001) {
                        salaryEditText.setError("the salary must be at least 2001");
                        doneImage.setVisibility(View.INVISIBLE);
                        step_done.setVisibility(View.INVISIBLE);
                        step_number.setVisibility(View.VISIBLE);
                        verif[7] = false;
                        verif[8] = false;

                    } else if (Integer.parseInt(charSequence.toString()) > 2001) {
                        doneImage.setVisibility(View.VISIBLE);
                        step_done.setVisibility(View.VISIBLE);
                        step_number.setVisibility(View.INVISIBLE);
                        configReward();
                        verif[7] = true;
                        verif[8] = true;
                    }
                } catch (NumberFormatException e) {
                    verif[7] = false;
                    verif[8] = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

/**********************************npo**************************************/

   //     npoSpinner = createSectorSpinner(R.array.jobtitle);



        final Call<List<UserDto>> users = retrofit.create(GetAllProfilWithoutImage.class).getUsers(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())));
        users.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void success(final Result<List<UserDto>> result) {
                ArrayList<String> usersName1 = new ArrayList<>();
                for (int i = 0; i < result.data.size(); i++) {
                    verif[9] = true;
                    if (null != result.data.get(i).getType()) {
                        if (result.data.get(i).getType().equals(NGO_KEY)) {
                            usersName1.add(result.data.get(i).getLogin());
                        }
                    }
                }

                npoSpinner = createSectorSpinner(usersName1);
                npoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    ImageView step_done = (ImageView) ngo.findViewById(R.id.step_done);
                    TextView step_number = (TextView) ngo.findViewById(R.id.step_number);
                    int check = 0;

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        jobLead.setNgo(adapterView.getItemAtPosition(i).toString());
                        verif[4] = false;
                        if (++check > 1) {
                            verif[4] = true;
                            step_done.setVisibility(View.VISIBLE);
                            step_number.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                stepLayoutInstall(ngo, mySteps[9], "10", npoSpinner);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("nizarab","failed");
            }
        });

        ngo = (LinearLayout) view.findViewById(R.id.ngo);



       /*
        TextView ngoStep_title = (TextView) ngo.findViewById(R.id.step_title);
        ngoStep_title.setText(mySteps[9]);
        TextView ngoStep_number = (TextView) ngo.findViewById(R.id.step_number);
        ngoStep_number.setText("10");
        RelativeLayout ngoStep_Header = (RelativeLayout) ngo.findViewById(R.id.step_header);
        ngoStep_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) ngo.findViewById(R.id.screen_content);

                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
        ngoEditText = (EditText) ngo.findViewById(R.id.textInput);
        ngoEditText.setHint(mySteps[9]);
        ngoEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        ngoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView doneImage = (ImageView) ngo.findViewById(R.id.doneImage);
                ImageView step_done = (ImageView) ngo.findViewById(R.id.step_done);
                TextView step_number = (TextView) ngo.findViewById(R.id.step_number);
                if (charSequence.length() < 4) {
                    ngoEditText.setError("the ngo must have at least 4 characters");
                    doneImage.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.INVISIBLE);
                    step_number.setVisibility(View.VISIBLE);
                    verif[9] = false;
                } else {
                    doneImage.setVisibility(View.VISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                    verif[9] = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

        final LinearLayout sector = (LinearLayout) view.findViewById(R.id.sector);
        Spinner sectorSpinner = createSectorSpinner(R.array.sector);

        sectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ImageView step_done = (ImageView) sector.findViewById(R.id.step_done);
            TextView step_number = (TextView) sector.findViewById(R.id.step_number);
            int check = 0;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobLead.setSector(adapterView.getItemAtPosition(i).toString());
                verif[4] = false;
                if (++check > 1) {
                    verif[4] = true;
                    step_done.setVisibility(View.VISIBLE);
                    step_number.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stepLayoutInstall(sector, mySteps[2], "5", sectorSpinner);

        final LinearLayout experience = (LinearLayout) view.findViewById(R.id.experience);
        Spinner experienceSpinner = createSectorSpinner(R.array.experience);
        experienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ImageView step_done = (ImageView) experience.findViewById(R.id.step_done);
            TextView step_number = (TextView) experience.findViewById(R.id.step_number);
            int check = 0;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobLead.setExperience(Integer.toString(i));
                verif[6] = false;
                if (++check > 1) {
                    verif[6] = true;
                    step_number.setVisibility(View.INVISIBLE);
                    step_done.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stepLayoutInstall(experience, mySteps[6], "7", experienceSpinner);

        final LinearLayout availability = (LinearLayout) view.findViewById(R.id.availability);
        stepLayoutInstall(availability, mySteps[7], "11", createAvailability(availability));

        final LinearLayout rewardChoosen = (LinearLayout) view.findViewById(R.id.rewardchoosen);
        stepLayoutInstall(rewardChoosen, mySteps[10], "9", createSlider(rewardChoosen));

        postulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isUserCompletesForm = true;
                for (Boolean v : verif) {
                    isUserCompletesForm = isUserCompletesForm & v;
                }
                if (isUserCompletesForm) {
                    setJobLeadData();
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    progressDialog.setMessage(getString(R.string.vertical_form_stepper_form_sending_data_message));

                    final Call<JobLeadsDto> jobLeadList = retrofit.create(PostJobLeads.class).postJobLeads(BEARER.concat(PreferencesStorageService.getDefaults(PREFERENCES_TOKEN_KEY, getApplicationContext())), jobLead);
                    jobLeadList.enqueue(new Callback<JobLeadsDto>() {
                        @Override
                        public void success(Result<JobLeadsDto> result) {
                            progressDialog.cancel();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    TimeLineFragment timeLineFragment = new TimeLineFragment();
                                    transaction.replace(R.id.accueilLineaireLayout, timeLineFragment);
                                    transaction.commit();
                                    sweetAlertDialog.cancel();
                                }
                            }).show();
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            Log.d("nizarabd", exception.toString());
                        }
                    });
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please complete the form !").show();
                }
            }
        });
        return view;
    }

    private void setJobLeadData() {
        jobLead.setTitle(jobTitleEditText.getText().toString());
        // jobLead.setMissionDesc(missionDescriptionEditText.getText().toString());
        jobLead.setCompanyName(companyNameEditText.getText().toString());
        jobLead.setCompanyDesc(companyDescriptionEditText.getText().toString());
        jobLead.setProfileSearch(profilSearchEditText.getText().toString());
        jobLead.setSalary(salaryEditText.getText().toString());
        jobLead.setCompanyName(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, getApplicationContext()));
    }

    private void stepLayoutInstall(final LinearLayout linearLayout, String title, String stepNumber, View view) {
        RelativeLayout step_content = (RelativeLayout) linearLayout.findViewById(R.id.step_content);
        step_content.addView(view);
        TextView step_title = (TextView) linearLayout.findViewById(R.id.step_title);
        step_title.setText(title);
        TextView step_number = (TextView) linearLayout.findViewById(R.id.step_number);
        step_number.setText(stepNumber);
        RelativeLayout step_Header = (RelativeLayout) linearLayout.findViewById(R.id.step_header);
        step_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout screen_content = (LinearLayout) linearLayout.findViewById(R.id.screen_content);
                if (screen_content.getVisibility() == View.GONE) {
                    screen_content.setVisibility(View.VISIBLE);
                } else {
                    screen_content.setVisibility(View.GONE);
                }
            }
        });
    }

    private DiscreteSeekBar createSlider(final LinearLayout linearLayout) {
        seekBar = new DiscreteSeekBar(getActivity());
        seekBar.setScrubberColor(ContextCompat.getColor(getApplicationContext(), R.color.jaune));
        seekBar.setTrackColor(ContextCompat.getColor(getApplicationContext(), R.color.jaune));
        seekBar.setThumbColor(ContextCompat.getColor(getApplicationContext(), R.color.jaune), ContextCompat.getColor(getApplicationContext(), R.color.jaune));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        seekBar.setLayoutParams(layoutParams);
        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            ImageView step_done = (ImageView) linearLayout.findViewById(R.id.step_done);
            TextView step_number = (TextView) linearLayout.findViewById(R.id.step_number);

            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                jobLead.setRewardChoosen(Integer.toString(value));
                step_done.setVisibility(View.VISIBLE);
                step_number.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
        return seekBar;
    }

    private void configReward() {
        jobLead.setRewardMin(Integer.toString(2000));
        jobLead.setRewardMax(salaryEditText.getText().toString());
        seekBar.setMin(Integer.parseInt(jobLead.getRewardMin()));
        seekBar.setMax(Integer.parseInt(jobLead.getRewardMax()));
        seekBar.setProgress(Integer.parseInt(jobLead.getRewardMax()) / 9);
    }

    private Spinner createSectorSpinner(int arrayId) {
        Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), arrayId, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        return spinner;
    }
    private Spinner createSectorSpinner(ArrayList<String> arrayList) {
        Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity() , android.R.layout.simple_spinner_item,arrayList);
        spinner.setAdapter(adapter);
        return spinner;
    }


    private TextView createAvailability(final LinearLayout linearLayout) {
        availability = new TextView(getActivity());
        availability.setText("1-1-2012");
        availability.setSingleLine(true);

        dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            ImageView step_done = (ImageView) linearLayout.findViewById(R.id.step_done);
            TextView step_number = (TextView) linearLayout.findViewById(R.id.step_number);

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                availability.setText(date);
                step_done.setVisibility(View.VISIBLE);
                step_number.setVisibility(View.INVISIBLE);
                verif[10] = true;
                jobLead.setAvailability(date);

            }
        }, 2015, 5, 22);
        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });
        return availability;
    }

}
