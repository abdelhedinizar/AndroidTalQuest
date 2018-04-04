package com.forsyslab.talquest10;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Created by LENOVO on 06/03/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    //launch main Activity
    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.signInMail)).perform(typeText("nizar"),closeSoftKeyboard());
        onView(withId(R.id.signInPassword)).perform(typeText("nizar123"),closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void simpleTrueLogin() throws Exception {
        onView(withId(R.id.signInMail)).perform(typeText("nizar"),closeSoftKeyboard());
        onView(withId(R.id.signInPassword)).perform(typeText("nizar123"),closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
    }
    @Test
    public void loginWithTwoClickButton() throws Exception {

        onView(withId(R.id.signInMail)).perform(typeText("nizar"),closeSoftKeyboard());
        onView(withId(R.id.signInPassword)).perform(typeText("nizar123"),closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
    }


    @Test
    public void clickButton() throws Exception {
        onView(withId(R.id.signInMail)).perform(typeText("nizar"),closeSoftKeyboard());
        onView(withId(R.id.signInPassword)).perform(typeText("nizar123"),closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.signInButton)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void LoginWithFacebook() throws Exception {
        onView(withId(R.id.signInFacebook)).perform(click());

       }


}
