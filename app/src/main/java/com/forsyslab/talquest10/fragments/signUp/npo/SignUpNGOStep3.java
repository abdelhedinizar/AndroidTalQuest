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

/**
 * Created by LENOVO on 26/01/2017.
 */

public class SignUpNGOStep3 extends Fragment {

    UserDto ngo;

    EditText companyDescription;
    EditText creationDate;
    EditText nbreOfEmployees;
    EditText projetDescription;
    EditText partners;
    EditText headQuarter;
    FloatingActionButton button;

    public SignUpNGOStep3(UserDto ngo) {
        this.ngo = ngo;
    }

    public SignUpNGOStep3() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ngo_step3_sign_up, container, false);
        companyDescription = (EditText) view.findViewById(R.id.signUpCompanyDescription);
        creationDate = (EditText) view.findViewById(R.id.signUpCreationDate);
        nbreOfEmployees = (EditText) view.findViewById(R.id.signUpNbreOfEmployees);
        projetDescription = (EditText) view.findViewById(R.id.signUpProjetDescription);
        partners = (EditText) view.findViewById(R.id.signUpPartners);
        headQuarter = (EditText)view.findViewById(R.id.signUpHeadQuarter);

        button = (FloatingActionButton) view.findViewById(R.id.step4Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngo.setDescription(companyDescription.getText().toString());
                ngo.setCreatedDate(creationDate.getText().toString());
                ngo.setNoEmp(nbreOfEmployees.getText().toString());
                ngo.setProjectsDescription(projetDescription.getText().toString());
                ngo.setPartners(partners.getText().toString());
                ngo.setHq(headQuarter.getText().toString());

                FragmentManager manager = getActivity().getSupportFragmentManager();

                SignUpNGOStep4 signUpStep4 = new SignUpNGOStep4(ngo);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment,signUpStep4);
                transaction.commit();

            }
        });
        return view;
    }

}
