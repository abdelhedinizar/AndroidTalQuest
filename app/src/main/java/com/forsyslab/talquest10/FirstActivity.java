package com.forsyslab.talquest10;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.forsyslab.talquest10.model.User;
import com.forsyslab.talquest10.storage.PreferencesStorageService;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forsyslab.talquest10.constant.Const.COMPANY_KEY;
import static com.forsyslab.talquest10.constant.Const.IS_USER_CONNECTED;
import static com.forsyslab.talquest10.constant.Const.NGO_KEY;
import static com.forsyslab.talquest10.constant.Const.PROFIL;
import static com.forsyslab.talquest10.constant.Const.TAG;
import static com.forsyslab.talquest10.constant.Const.USER_KEY;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.createAccount)
    Button createAccount;
    @BindView(R.id.text)
    HTextView hTextView;
    @BindView(R.id.text1)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (PreferencesStorageService.getDefaults(IS_USER_CONNECTED, getApplicationContext()) == null) {
            PreferencesStorageService.setDefaults(IS_USER_CONNECTED, "false", getApplicationContext());
        }

        /* just for test
        PreferencesStorageService.setDefaults(PROFIL,"{\n" +
                "\"login\": \"nizar\",\n" +
                "\"firstName\": \"abdelhedi\",\n" +
                "\"email\": \"nizar@gmail.com\",\n" +
                "\"lastName\": \"Nizar\",\n" +
                "\"activated\": true,\n" +
                "\"langKey\": null,\n" +
                "\"authorities\": [\n" +
                "  \"ROLE_USER\"\n" +
                "],\n" +
                "\"country\": \"Tunisia\",\n" +
                "\"postcode\": \"3021\",\n" +
                "\"jobTitle\": \"Developer\",\n" +
                "\"company\": \"forsysLab\",\n" +
                "\"sector\": \"Informatique, internet et télécom\",\n" +
                "\"type\": \"user\",\n" +
                "\"noEmp\": null,\n" +
                "\"description\": null,\n" +
                "\"hq\": null,\n" +
                "\"website\": null,\n" +
                "\"dateCreated\": null,\n" +
                "\"skills\": null,\n" +
                "\"experience\": null,\n" +
                "\"companyType\": null,\n" +
                "\"fbAccount\": null,\n" +
                "\"twitterAccount\": null,\n" +
                "\"googleAccount\": null,\n" +
                "\"linkedinAccount\": null,\n" +
                "\"logo\": null,\n" +
                "\"cover\": null,\n" +
                "\"projectsDescription\": null,\n" +
                "\"partners\": null,\n" +
                "\"companyPage_role\": null,\n" +
                "\"id\": \"58adb2600eb9ba5a45ea08cd\",\n" +
                "\"createdDate\": \"2017-02-22T15:46:40.543Z\",\n" +
                "\"lastModifiedBy\": \"anonymousUser\",\n" +
                "\"lastModifiedDate\": \"2017-02-22T15:46:40.543Z\",\n" +
                "\"password\": null\n" +
                "}",getApplicationContext());
*/
        if ("true".equals(PreferencesStorageService.getDefaults(IS_USER_CONNECTED, getApplicationContext()))) {
            String profil = PreferencesStorageService.getDefaults(PROFIL, getApplicationContext());
            Gson gson = new Gson();
            Log.d(TAG,profil);
            User userProfil = gson.fromJson(profil, User.class);
            if (userProfil.getType().equals(USER_KEY)) {
                Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                startActivity(intent);
            } else if (userProfil.getType().equals(COMPANY_KEY)) {
                Intent intent = new Intent(getApplicationContext(), AccueilCompanyActivity.class);
                startActivity(intent);
            } else if (userProfil.getType().equals(NGO_KEY)) {
                Intent intent = new Intent(getApplicationContext(), AccueilNGOActivity.class);
                startActivity(intent);
            }
            finish();
        }
        ButterKnife.bind(this);
        changeStatusBarColor();
        hTextView.animateText("Welcome to a new world of sharing!");
        textView1.setText("TalQuest is a Trust Social Platform that enables you to trust someone you don’t know, thanks to a new\n" +
                "way to manage your reputation. You can for instance help your friends find better jobs, \n" +
                "get reputation for this and give to Non-profits. And there’s more");
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(1)
                .playOn(findViewById(R.id.text1));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                hTextView.animateText("Share and prosper");
                textView1.setText("TalQuest is an augmented social network that shares the profits of the social media industry with its members \n" +
                        "(a person like you and Non-Profits). For instance, you can earn a minimum of $1334 to help a member be recruited,\n" +
                        "and will give 1% to 100% to an NGO (it’s up to you!). The more you act and give, the more reputation you earn.\n" +
                        "See what you can do");
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .repeat(1)
                        .playOn(findViewById(R.id.text1));

            }
        }, 3000);
        handler.postDelayed(new Runnable() {
            public void run() {
                hTextView.animateText("Save your privacy");
                textView1.setText("Data privacy and security are paramount to trust on social media. What could best fit TalQuest than\n" +
                        "the Blockchain, the revolutionary trust and transparency protocol? We are based on the Blockchain, \n" +
                        "so your personal information is encrypted, secured, and really belongs to you.");
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .repeat(1)
                        .playOn(findViewById(R.id.text1));
            }
        }, 6000);
    }
    @OnClick(R.id.createAccount)
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}