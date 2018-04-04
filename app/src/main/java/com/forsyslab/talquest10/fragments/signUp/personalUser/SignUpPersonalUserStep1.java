package com.forsyslab.talquest10.fragments.signUp.personalUser;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;

import static com.forsyslab.talquest10.constant.Const.USER_KEY;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasChiffre;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasEspace;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextInferiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextSuperiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEmailValid;

/**
 * Created by nizar on 19/01/2017.
 */

public class SignUpPersonalUserStep1 extends Fragment implements android.text.TextWatcher {


    UserDto user;
    EditText signUpMail;
    EditText signUpFirstName;
    EditText signUpLastName;
    FloatingActionButton button;

    @Override
    public void onResume() {
        super.onResume();
        addAllTextChangedListener();
        CheckInputsCharacteristics();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step1_sign_up, container, false);
        findAllViews(view);

        button.setEnabled(false);
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                user = new UserDto(signUpMail.getText().toString(), signUpFirstName.getText().toString(), signUpLastName.getText().toString(),USER_KEY);
                SignUpPersonalUserStep2 signUpStep2 = new SignUpPersonalUserStep2(user);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep2);
                transaction.commit();
            }
        });
        return view;
    }

    private void addAllTextChangedListener() {
        signUpMail.addTextChangedListener(this);
        signUpFirstName.addTextChangedListener(this);
        signUpLastName.addTextChangedListener(this);
    }

    private void findAllViews(View view) {
        signUpMail = (EditText) view.findViewById(R.id.signUpMail);
        signUpFirstName = (EditText) view.findViewById(R.id.signUpFirstName);
        signUpLastName = (EditText) view.findViewById(R.id.signUpLastName);
        button = (FloatingActionButton) view.findViewById(R.id.step2Button);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        CheckInputsCharacteristics();
    }

    private void CheckInputsCharacteristics() {
        if (isEditTextEmpty(signUpMail, getResources().getString(R.string.eMail))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (!isEmailValid(signUpMail.getText().toString())) {
            signUpMail.setError("please write your true mail");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextEmpty(signUpFirstName, getResources().getString(R.string.FirstName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextSuperiorThenN(signUpFirstName, (byte) 50, getResources().getString(R.string.FirstName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextInferiorThenN(signUpFirstName, (byte) 2, getResources().getString(R.string.FirstName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasEspace(signUpFirstName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasChiffre(signUpFirstName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextEmpty(signUpLastName, getResources().getString(R.string.LastName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextSuperiorThenN(signUpLastName, (byte) 50, getResources().getString(R.string.LastName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextInferiorThenN(signUpLastName, (byte) 2, getResources().getString(R.string.LastName))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasEspace(signUpLastName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasChiffre(signUpLastName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }
    }
}
