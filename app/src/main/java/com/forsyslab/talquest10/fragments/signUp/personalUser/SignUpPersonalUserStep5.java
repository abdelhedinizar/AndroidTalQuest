package com.forsyslab.talquest10.fragments.signUp.personalUser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.forsyslab.talquest10.R;
import com.forsyslab.talquest10.model.ModelDto.UserDto;
import com.forsyslab.talquest10.services.ImageService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LENOVO on 30/01/2017.
 */

public class SignUpPersonalUserStep5 extends Fragment {


    public SignUpPersonalUserStep5(UserDto profil) {
        this.profil = profil;
    }

    UserDto profil;
    public static final int RESULT_LOAD_PHOTO = 3;
    ImageView signUpPhoto;
    FloatingActionButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step5_sign_up, container, false);
        signUpPhoto = (ImageView) view.findViewById(R.id.signUpPhoto);
        signUpPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                startActivityForResult(chooserIntent, RESULT_LOAD_PHOTO);
            }
        });

        button = (FloatingActionButton) view.findViewById(R.id.step5Button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getActivity().getSupportFragmentManager();

                SignUpPersonalUserStep4 signUpStep4 = new SignUpPersonalUserStep4(profil);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("");
                transaction.setCustomAnimations(R.anim.fragment_anim_show, R.anim.fragment_anim_hide);
                transaction.replace(R.id.signUpForFragment, signUpStep4);
                transaction.commit();


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
            if (requestCode == RESULT_LOAD_PHOTO && resultCode == RESULT_OK
                    && null != data) {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = encodeImage(selectedImage);
                Log.d("nizarab",encodedImage);
                profil.setLogo(encodedImage);

                byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
                signUpPhoto.setImageBitmap(
                        ImageService.resize(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length))
                );

            }
            } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

}
