package com.forsyslab.talquest10.fragments.signUp.company;

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
import android.widget.EditText;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;

import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasChiffre;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextHasEspace;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextInferiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextSuperiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEmailValid;

/**
 * Created by LENOVO on 01/02/2017.
 */

public class SignUpCompanyStep1 extends Fragment implements android.text.TextWatcher{


    FloatingActionButton button;
    EditText signUpUserName;
    EditText signUpEmail;
    EditText signUpPassword;
    EditText signUpConfirmPassword;
    UserDto user;

    @Override
    public void onResume() {
        super.onResume();
        addAllTextChangedListener();
        checkInputsCharacteristics();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_step1_sign_up, container, false);

        signUpUserName = (EditText) view.findViewById(R.id.signUpUserName);
        signUpEmail = (EditText) view.findViewById(R.id.signUpMail);
        signUpPassword = (EditText) view.findViewById(R.id.signUpPassword);
        signUpConfirmPassword = (EditText) view.findViewById(R.id.signUpConfirmPassword);



        button = (FloatingActionButton) view.findViewById(R.id.step2Button);
        setEnabledFalse();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();

                    user = new UserDto();
                    user.setLogin(signUpUserName.getText().toString());
                    user.setEmail(signUpEmail.getText().toString());
                    user.setPassword(signUpPassword.getText().toString());
                    SignUpCompanyStep2 signUpStep2 = new SignUpCompanyStep2(user);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack("");
                    transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                    transaction.replace(R.id.signUpForFragment, signUpStep2);
                    transaction.commit();
            }
        });
        setEnabledFalse();
        return view;
    }

    public void addAllTextChangedListener() {
        signUpEmail.addTextChangedListener(this);
        signUpUserName.addTextChangedListener(this);
        signUpConfirmPassword.addTextChangedListener(this);
        signUpPassword.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        checkInputsCharacteristics();
    }

    public void checkInputsCharacteristics() {
        if (isEditTextEmpty(signUpUserName, getResources().getString(R.string.Username)))
        {
            setEnabledFalse();
        }
     else   if (isEditTextSuperiorThenN(signUpUserName, (byte) 50, getResources().getString(R.string.Username)))
        {
            setEnabledFalse();
        }
      else   if((isEditTextInferiorThenN(signUpUserName,(byte)2,getResources().getString(R.string.Username)))||((isEditTextHasEspace(signUpUserName)))||(isEditTextHasChiffre(signUpUserName))||(isEditTextEmpty(signUpEmail, "E-mail"))||(isEditTextHasEspace(signUpPassword)))
        {
            setEnabledFalse();
        }
      else  if (!isEmailValid(signUpEmail.getText().toString())) {
            signUpEmail.setError("please write your true mail");
            setEnabledFalse();
        }
        else if (isEditTextInferiorThenN(signUpPassword,(byte)8,"Password")){
            setEnabledFalse();
        }
        else if (!signUpPassword.getText().toString().equals(signUpConfirmPassword.getText().toString())) {
            signUpConfirmPassword.setError("your password is not equal to your confirm password");
            setEnabledFalse();
      }
        else
        {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }
    }

    private void setEnabledFalse() {
        button.setEnabled(false);
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
    }
}
