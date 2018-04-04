package com.forsyslab.talquest10.fragments.signUp.company;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;

import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasEspace;

/**
 * Created by LENOVO on 01/02/2017.
 */

public class SignUpCompanyStep3 extends Fragment implements android.text.TextWatcher {


    UserDto company;

    EditText companyDescription;
    EditText creationDate;
    EditText nbreOfEmployees;
    EditText projetDescription;
    EditText partners;
    EditText headQuarter;
    FloatingActionButton button;


    public SignUpCompanyStep3(UserDto company) {
        this.company = company;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_step3_sign_up, container, false);
        companyDescription = (EditText) view.findViewById(R.id.signUpCompanyDescription);
        creationDate = (EditText) view.findViewById(R.id.signUpCreationDate);
        nbreOfEmployees = (EditText) view.findViewById(R.id.signUpNbreOfEmployees);
        projetDescription = (EditText) view.findViewById(R.id.signUpProjetDescription);
        partners = (EditText) view.findViewById(R.id.signUpPartners);
        headQuarter = (EditText) view.findViewById(R.id.signUpHeadQuarter);

        creationDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            creationDate.setText(date);

                        }
                    }, 2015, 5, 22);

                    dpd.show();

                }
            }
        });

        button = (FloatingActionButton) view.findViewById(R.id.step4Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                company.setDescription(companyDescription.getText().toString());
                company.setCreatedDate(creationDate.getText().toString());
                company.setNoEmp(nbreOfEmployees.getText().toString());
                company.setProjectsDescription(projetDescription.getText().toString());
                company.setPartners(partners.getText().toString());
                company.setHq(headQuarter.getText().toString());

                FragmentManager manager = getActivity().getSupportFragmentManager();

                SignUpCompanyStep4 signUpStep4 = new SignUpCompanyStep4(company);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep4);
                transaction.commit();

            }
        });
        button.setEnabled(false);
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));

        creationDate.addTextChangedListener(this);
        nbreOfEmployees.addTextChangedListener(this);

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
        if (isEditTextEmpty(creationDate, getResources().getString(R.string.creationDate))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasEspace(creationDate)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        }
        else if(isEditTextEmpty(nbreOfEmployees,getResources().getString(R.string.nbreOfemployee)))
        {

        }


        else {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }


    }
}
