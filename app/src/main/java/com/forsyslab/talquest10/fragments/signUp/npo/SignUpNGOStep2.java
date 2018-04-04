package com.forsyslab.talquest10.fragments.signUp.npo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by companyName on 25/01/2017.
 */

public class SignUpNGOStep2 extends Fragment {
    UserDto ngo;
    MaterialSpinner signUpCountry;
    EditText signUpPostCode;
    ImageView signUpCoverPhoto;
    ImageView signUpLogo;
    FloatingActionButton button;
    private static int RESULT_LOAD_LOGO = 1;
    private static int RESULT_LOAD_COVER = 2;

    String logoDecodableString;
    String coverDecodableString;


    public SignUpNGOStep2(UserDto ngo) {
        this.ngo = ngo;
    }

    public SignUpNGOStep2() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ngo_step2_sign_up, container, false);
        signUpCountry = (MaterialSpinner) view.findViewById(R.id.spinner);
        signUpPostCode = (EditText) view.findViewById(R.id.signUpPostCode);
        signUpLogo = (ImageView) view.findViewById(R.id.signUpLogo);

        signUpLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_LOGO);

            }
        });

        signUpCoverPhoto = (ImageView) view.findViewById(R.id.signUpCoverPhoto);
        signUpCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_COVER);
            }
        });


        button = (FloatingActionButton) view.findViewById(R.id.step3Button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngo.setPostcode(signUpPostCode.getText().toString());
                ngo.setLogo(logoDecodableString);
                ngo.setCover(coverDecodableString);
                FragmentManager manager = getActivity().getSupportFragmentManager();

                SignUpNGOStep3 signUpStep3 = new SignUpNGOStep3(ngo);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep3);
                transaction.commit();
            }
        });
        signUpCountry.setItems(getResources().getStringArray(R.array.countries));
        signUpCountry.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                ngo.setCountry(item);
                Log.d("companyName",ngo.getCountry());
            }
        });

        return view;
    }


    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_LOGO && resultCode == RESULT_OK
                    && null != data) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = encodeImage(selectedImage);
                Log.d("nizarab",encodedImage);
                ngo.setLogo(encodedImage);
                byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
                signUpLogo.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_COVER && resultCode == RESULT_OK
                    && null != data) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = encodeImage(selectedImage);
                Log.d("nizarab",encodedImage);
                ngo.setCover(encodedImage);
                byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
                signUpCoverPhoto.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


    }

}
