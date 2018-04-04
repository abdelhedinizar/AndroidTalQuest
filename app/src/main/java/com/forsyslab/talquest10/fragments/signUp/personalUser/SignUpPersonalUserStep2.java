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

import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextBeginWithSpace;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasChiffre;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasEspace;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextInferiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextSuperiorThenN;

/**
 * Created by nizar on 19/01/2017.
 */

public class SignUpPersonalUserStep2 extends Fragment implements android.text.TextWatcher {


    UserDto user;
    EditText userName;
    EditText password;
    EditText confirmPassword;
    FloatingActionButton button;

    @Override
    public void onResume() {
        super.onResume();
        addAllTextChangedListener();
        CheckInputsCharacteristics();
    }



    public SignUpPersonalUserStep2(UserDto user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step2_sign_up, container, false);
        findAllViews(view);

        button.setEnabled(false);
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setLogin(userName.getText().toString());
                user.setPassword(password.getText().toString());
                FragmentManager manager = getActivity().getSupportFragmentManager();

                SignUpPersonalUserStep3 signUpStep3 = new SignUpPersonalUserStep3(user);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep3);
                transaction.commit();
            }
        });


        return view;
    }

    private void findAllViews(View view) {
        userName = (EditText) view.findViewById(R.id.signUpUserName);
        password = (EditText) view.findViewById(R.id.signUpPassword);
        confirmPassword = (EditText) view.findViewById(R.id.signUpConfirmPassword);
        button = (FloatingActionButton) view.findViewById(R.id.step3Button);
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
        if (isEditTextEmpty(userName, getResources().getString(R.string.Username))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextSuperiorThenN(userName, (byte) 50, getResources().getString(R.string.Username))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextInferiorThenN(userName, (byte) 2, getResources().getString(R.string.Username))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasEspace(userName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextHasChiffre(userName)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextInferiorThenN(password, (byte) 8, getResources().getString(R.string.Password))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (isEditTextBeginWithSpace(password)) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("your password is not equal to your confirm password");
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }
    }

    private void addAllTextChangedListener() {
        userName.addTextChangedListener(this);
        password.addTextChangedListener(this);
        confirmPassword.addTextChangedListener(this);
    }


}
