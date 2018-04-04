package com.forsyslab.talquest10.fragments.signUp.personalUser;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.jaredrummler.materialspinner.MaterialSpinner;

import static com.forsyslab.talquest10.constant.PostCodeRegrex.POSTCODE_REGREX;
import static com.forsyslab.talquest10.services.UserInputVerification.isEditTextEmpty;
import static com.forsyslab.talquest10.services.UserInputVerification.isPostCodeValid;

/**
 * Created by companyName on 19/01/2017.
 */
public class SignUpPersonalUserStep3 extends Fragment implements android.text.TextWatcher {


    UserDto user;
    MaterialSpinner country;
    EditText postCode;
    FloatingActionButton button;
    int countryPosition = 0;

    @Override
    public void onResume() {
        super.onResume();
        postCode.addTextChangedListener(this);
        CheckInputsCharacteristics();
    }


    public SignUpPersonalUserStep3(UserDto user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step3_sign_up, container, false);
        country = (MaterialSpinner) view.findViewById(R.id.signUpCountry);


        country.setItems(getResources().getStringArray(R.array.countries));
        country.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                user.setCountry(item);
                countryPosition = position;
                Log.d("companyName",position+" ");
            }
        });
        postCode = (EditText) view.findViewById(R.id.signUpPostCode);

/*        postCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new MaterialDialog.Builder(getActivity()).title("shoose your Country").items(getResources().getStringArray(R.array.countries)).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence availability) {
                        Toast.makeText(getActivity(),availability.toString(),Toast.LENGTH_LONG).show();
                        return false;
                    }
                }).positiveText("Ok").show();
            }
        });
*/


        button = (FloatingActionButton) view.findViewById(R.id.step4Button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setCountry(country.getText().toString());
                user.setPostcode(postCode.getText().toString());

                FragmentManager manager = getActivity().getSupportFragmentManager();

                //        SignUpPersonalUserStep4 signUpStep4 = new SignUpPersonalUserStep4(user);
                SignUpPersonalUserStep5 signUpStep5 = new SignUpPersonalUserStep5(user);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep5);
                transaction.commit();
            }
        });
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

        CheckInputsCharacteristics();
    }

    private void CheckInputsCharacteristics() {
        if (isEditTextEmpty(postCode, getResources().getString(R.string.Postcode))) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
        } else if (!isPostCodeValid(postCode.getText().toString(), POSTCODE_REGREX[countryPosition])) {
            button.setEnabled(false);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gris)));
            postCode.setError("error");
        } else {
            button.setEnabled(true);
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }

    }
}
