package com.forsyslab.talquest10.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.forsyslab.talquest10.R;

import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextInferiorThenN;

/**
 * Created by LENOVO on 23/02/2017.
 */

public class ChangePasswordFragment extends Fragment implements android.text.TextWatcher {


    EditText changePassword;
    EditText confirmPassword;
    Button changePasswordButton;

    @Override
    public void onResume() {
        super.onResume();
        addAllTextChangedListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        findAllViews(view);
        addAllTextChangedListeners();


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;

    }

    private void addAllTextChangedListeners() {
        changePassword.addTextChangedListener(this);
        confirmPassword.addTextChangedListener(this);
    }

    private void findAllViews(View view) {
        changePassword = (EditText) view.findViewById(R.id.changePassword);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        changePasswordButton = (Button) view.findViewById(R.id.changePasswordButton);
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

    public void CheckInputsCharacteristics(){
        if(isEditTextInferiorThenN(changePassword,(byte)8,getResources().getString(R.string.Password)));
        {
            changePasswordButton.setClickable(false);
        }
        if(!changePassword.getText().toString().equals(confirmPassword.getText().toString()))
        {
            confirmPassword.setError("your password is not equal to your confirm password");
            changePasswordButton.setClickable(false);
        }
        else
        {
            changePasswordButton.setClickable(true);

        }
    }
}
