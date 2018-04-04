package com.forsyslab.talquest10;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.forsyslab.talquest10.constant.Const;
import com.forsyslab.talquest10.dagger.AndroidApplication;
import com.forsyslab.talquest10.dagger.Authentification;
import com.forsyslab.talquest10.model.Authenticate;
import com.forsyslab.talquest10.model.IdToken;

import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.forsyslab.talquest10.constant.Const.A_NETWORK_ERROR_HAS_OCCURRED;
import static com.forsyslab.talquest10.constant.Const.IS_USER_CONNECTED;
import static com.forsyslab.talquest10.constant.Const.JSON_PASSWORD;
import static com.forsyslab.talquest10.constant.Const.JSON_REMEMBER_ME;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_LOGIN;
import static com.forsyslab.talquest10.constant.Const.PREFERENCES_TOKEN_KEY;
import static com.forsyslab.talquest10.constant.Const.TAG;
import static com.forsyslab.talquest10.constant.Const.TWITTER_KEY;
import static com.forsyslab.talquest10.constant.Const.TWITTER_SECRET;
import static com.forsyslab.talquest10.constant.Const.USER_TYPE;
import static com.forsyslab.talquest10.services.ConnectionService.isThereInternetConnection;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    static final int RC_SIGN_IN = 5;

    Authenticate authenticate;

    @BindView(R.id.login_button)
    LoginButton facebookLoginButton;
    @BindView(R.id.twitter_login_button)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.signInMail)
    EditText usermail;
    @BindView(R.id.signInPassword)
    EditText userPassword;
    @BindView(R.id.signInButton)
    Button signInButton;
    @BindView(R.id.signUpTextView)
    TextView signUpTextview;
    @BindView(R.id.signInFacebook)
    Button signInFacebook;
    @BindView(R.id.signInGoogle)
    Button signInGoogle;
    @BindView(R.id.signInTwitter)
    Button signInTwitter;
    @BindView(R.id.rememberMe)
    CheckBox rememberMe;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout container;
    byte userType;

    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeStatusBarColor();
        ((AndroidApplication) getApplicationContext()).getNetComponent().inject(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        container.startShimmerAnimation();

        if (PreferencesStorageService.getDefaults(JSON_REMEMBER_ME, getApplicationContext()) == null) {
            PreferencesStorageService.setDefaults(JSON_REMEMBER_ME, "false", getApplicationContext());
        } else if ("true".equals(PreferencesStorageService.getDefaults(JSON_REMEMBER_ME, getApplicationContext()))) {
            usermail.setText(PreferencesStorageService.getDefaults(PREFERENCES_LOGIN, getApplicationContext()));
            userPassword.setText(PreferencesStorageService.getDefaults(JSON_PASSWORD, getApplicationContext()));
        }
        //**************************************facebook login*****************************************************************
        facebookLoginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                PreferencesStorageService.setDefaults(PREFERENCES_LOGIN, profile.getFirstName() + " " + profile.getLastName(), getApplicationContext());
                PreferencesStorageService.setDefaults(IS_USER_CONNECTED,Boolean.toString(true),getApplicationContext());
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.getMessage());
            }
        });

        //*****************************************************************************************************

        //*********************************google login in *****************************************************
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        //***************************************************************************************************************
        //***********************************twitter login**************************************************************

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                result.data.getUserName();
                twitterLogin(result);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        //*********************************************fin twitter login ***************************************************************
    }


    @OnClick(R.id.signUpTextView)
    public void onClick() {
        int jaune = ContextCompat.getColor(getApplicationContext(),R.color.jaune);
        new MaterialDialog.Builder(MainActivity.this).title(R.string.signIn).items(R.array.userType).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                selectUserType(which);
                return true;
            }
        })
                .widgetColor(jaune).positiveText(R.string.choose)
                .titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.grisa)
                .positiveColor(jaune)
                .show();
    }

    @OnClick(R.id.signInButton)
    public void signInOnClick() {
        if (!isThereInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), A_NETWORK_ERROR_HAS_OCCURRED, Toast.LENGTH_LONG).show();
            return;
        }
            PreferencesStorageService.setDefaults(JSON_REMEMBER_ME, Boolean.toString(rememberMe.isChecked()), getApplicationContext());

         authenticate = new Authenticate(usermail.getText().toString(), userPassword.getText().toString(), rememberMe.isChecked());
        Class<Authentification> Authenticate = Authentification.class;
        final Call<IdToken> idTokenCall = retrofit.create(Authenticate).getIdToken(authenticate);


            idTokenCall.enqueue(new Callback<IdToken>() {
                @Override
                public void success(Result<IdToken> result) {
                    PreferencesStorageService.setDefaults(PREFERENCES_TOKEN_KEY, result.data.getId_token(), getApplicationContext());
                    PreferencesStorageService.setDefaults(PREFERENCES_LOGIN, authenticate.getUsername(), getApplicationContext());
                    PreferencesStorageService.setDefaults(JSON_PASSWORD, authenticate.getPassword(), getApplicationContext());
                    Log.d(PREFERENCES_TOKEN_KEY, result.data.getId_token());
                    PreferencesStorageService.setDefaults(IS_USER_CONNECTED,Boolean.toString(true),getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    finish();
                }
                @Override
                public void failure(TwitterException exception) {
                    Log.d("nizarab",exception.getLocalizedMessage());
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("The password or mail you entered is incorrect!\nPlease try again.")
                            .setConfirmText(getString(R.string.try_again)).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(getApplicationContext(),AccueilCompanyActivity.class));
                        }
                    }).show();
                }
            });

        }
   @OnClick(R.id.signInFacebook)
    public void faceBookOnClick() {
        if (!isThereInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), A_NETWORK_ERROR_HAS_OCCURRED, Toast.LENGTH_LONG).show();
            return;
        }
        facebookLoginButton.callOnClick();
    }

    @OnClick(R.id.signInGoogle)
    public void googleOnClick(View view) {
        if (!isThereInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), A_NETWORK_ERROR_HAS_OCCURRED, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }
    @OnClick(R.id.signInTwitter)
    public void twitterOnClick() {
        if (!isThereInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), A_NETWORK_ERROR_HAS_OCCURRED, Toast.LENGTH_LONG).show();
            return;
        }
        twitterLoginButton.callOnClick();
    }
    private void selectUserType(int which) {
        if (which == -1) {
            Toast.makeText(MainActivity.this, "shoose", Toast.LENGTH_LONG).show();
            new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.signIn)
                    .content("you didn't answer my question")
                    .positiveText("Ok")
                    .negativeText("Cancel")
                    .show();
        }
        if (which == 0) {
            userType = Const.RESEARCHER_JOB_Type;
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            intent.putExtra(USER_TYPE, userType);
            startActivity(intent);
        }
        if (which == 1) {
            userType = Const.NGO_TYPE;
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            intent.putExtra(USER_TYPE, userType);
            startActivity(intent);
        }
        if (which == 2) {
            userType = Const.COMPANY_TYPE;
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            intent.putExtra(USER_TYPE, userType);
            startActivity(intent);
        }
    }
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
            PreferencesStorageService.setDefaults(PREFERENCES_LOGIN, googleSignInAccount.getGivenName() + " " + googleSignInAccount.getFamilyName(), getApplicationContext());
            PreferencesStorageService.setDefaults(IS_USER_CONNECTED,Boolean.toString(true),getApplicationContext());
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();

        } else {
            Toast.makeText(this, "something doesn't work google sign in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void twitterLogin(Result<TwitterSession> result) {

        TwitterSession session = result.data;

        Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false).enqueue(new Callback<User>() {

            @Override
            public void success(Result<User> userResult) {
                try {
                    User user = userResult.data;
                    //twitterImage = user.profileImageUrl;
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
            }
            @Override
            public void failure(TwitterException e) {
            }

        });
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        finish();
        PreferencesStorageService.setDefaults(IS_USER_CONNECTED,Boolean.toString(true),getApplicationContext());
        startActivity(intent);

    }

}
