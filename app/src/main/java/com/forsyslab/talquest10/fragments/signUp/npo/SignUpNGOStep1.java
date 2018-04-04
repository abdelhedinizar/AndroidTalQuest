package com.forsyslab.talquest10.fragments.signUp.npo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;

import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextSuperiorThenN;
import static com.forsyslab.talquest10.services.UserInputVerification.isEmailValid;

/**
 * Created by nizar on 25/01/2017.
 */

public class SignUpNGOStep1 extends Fragment {


    FloatingActionButton floatingActionButton;
    EditText signUpUserName;
    EditText signUpEmail;
    EditText signUpPassword;
    EditText signUpConfirmPassword;
    UserDto user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ngo_step1_sign_up, container, false);

        signUpUserName = (EditText) view.findViewById(R.id.signUpUserName);
        signUpEmail = (EditText) view.findViewById(R.id.signUpMail);
        signUpPassword = (EditText) view.findViewById(R.id.signUpPassword);
        signUpConfirmPassword = (EditText) view.findViewById(R.id.signUpConfirmPassword);


        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.step2Button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();

                if (isEditTextEmpty(signUpUserName, "Username"))
                    return;
                else if (isEditTextSuperiorThenN(signUpUserName, (byte) 50, "Username"))
                    return;
                else if (isEditTextEmpty(signUpEmail, "E-mail"))
                    return;
                else if (!isEmailValid(signUpEmail.getText().toString())) {
                    signUpEmail.setError("please write your true mail");
                    signUpEmail.requestFocus();
                } else if (!signUpPassword.getText().toString().equals(signUpConfirmPassword.getText().toString())) {
                    signUpConfirmPassword.setError("your password is not equal to your confirm password");
                    signUpConfirmPassword.requestFocus();
                    return;
                } else {
                    user = new UserDto();
                    user.setLogin(signUpUserName.getText().toString());
                    user.setEmail(signUpEmail.getText().toString());
                    user.setPassword(signUpPassword.getText().toString());
                    SignUpNGOStep2 signUpStep2 = new SignUpNGOStep2(user);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack("");
                    transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                    transaction.replace(R.id.signUpForFragment, signUpStep2);
                    transaction.commit();
                }

            }
        });


        return view;
    }
}
