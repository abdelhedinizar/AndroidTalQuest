package com.forsyslab.talquest10;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forsyslab.talquest10.fragments.signUp.company.SignUpCompanyStep1;
import com.forsyslab.talquest10.fragments.signUp.npo.SignUpNGOStep1;
import com.forsyslab.talquest10.fragments.signUp.personalUser.SignUpPersonalUserStep1;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

public class SignUpActivity extends AppCompatActivity {

    private GyroscopeObserver gyroscopeObserver;

    public SignUpActivity(){


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        byte b=0;
        b=getIntent().getByteExtra("userType",b);

        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI/2);

        PanoramaImageView panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);
        // Set GyroscopeObserver for PanoramaImageView.
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);

        if(b==1)
        {
            SignUpPersonalUserStep1 signUpStep1 = new SignUpPersonalUserStep1();
            transaction.replace(R.id.signUpForFragment, signUpStep1);
        }
        else if(b==2)
        {
            SignUpNGOStep1 signUpNGOStep1 = new SignUpNGOStep1();
            transaction.replace(R.id.signUpForFragment,signUpNGOStep1);
        }
        else if(b==3)
        {
            SignUpCompanyStep1 signUpCompanyStep1 = new SignUpCompanyStep1();
            transaction.replace(R.id.signUpForFragment,signUpCompanyStep1);
        }

        transaction.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
