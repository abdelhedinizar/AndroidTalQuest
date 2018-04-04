package com.forsyslab.talquest10;

import com.forsyslab.talquest10.services.UserInputVerification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class InputRegistrationUnitTest {



    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(UserInputVerification.isEmailValid("nizar@email.fr"), is(true));
        assertThat(UserInputVerification.isEmailValid("abdelhedinizar@gmail.com"), is(true));
        assertThat(UserInputVerification.isEmailValid("nizar.abdelhedi@enis.tn"), is(true));
        assertThat(UserInputVerification.isEmailValid("nizar.abdelhedi@forsyslab.com"), is(true));
        assertThat(UserInputVerification.isEmailValid("nizar@.fr"), is(false));
        assertThat(UserInputVerification.isEmailValid("nizar@abdelhedi"), is(false));
        assertThat(UserInputVerification.isEmailValid("nizarabdelhedi.fr"), is(false));
        assertThat(UserInputVerification.isEmailValid("nizarabdelhedi"), is(false));
    }

    @Test
    public void isPostCodeValid() {
        assertThat(UserInputVerification.isPostCodeValid("30215", "^(\\d{5})$"), is(true));
    }

    @Test
    public void isEmpty() {
        assertThat(UserInputVerification.isEmpty(""), is(true));
        assertThat(UserInputVerification.isEmpty("nizar"), is(false));
        assertThat(UserInputVerification.isEmpty("dskl,"), is(false));
        assertThat(UserInputVerification.isEmpty(""), is(true));
        assertThat(UserInputVerification.isEmpty("sd"), is(false));
    }

    @Test
    public void isStrHasChiffre() {
        assertThat(UserInputVerification.isStrHasChiffre("nizar15"), is(true));
        assertThat(UserInputVerification.isStrHasChiffre("nizar"), is(false));
        assertThat(UserInputVerification.isStrHasChiffre("nizar1"), is(true));
        assertThat(UserInputVerification.isStrHasChiffre("n1izar"), is(true));
        assertThat(UserInputVerification.isStrHasChiffre("0nizar"), is(true));
        assertThat(UserInputVerification.isStrHasChiffre("niz*"), is(true));
        assertThat(UserInputVerification.isStrHasChiffre("/nsddss"), is(false));
    }

    @Test
    public void isStrHasEspace() {
        assertThat(UserInputVerification.isStrHasEspace(" nizar"), is(true));
        assertThat(UserInputVerification.isStrHasEspace("n izar"), is(true));
        assertThat(UserInputVerification.isStrHasEspace("nizar "), is(true));
        assertThat(UserInputVerification.isStrHasEspace("   n  izar"), is(true));
        assertThat(UserInputVerification.isStrHasEspace(""), is(false));
        assertThat(UserInputVerification.isStrHasEspace("nizar"), is(false));
    }

    @Test
    public void isStringBeginWithSpace(){
        assertThat(UserInputVerification.isStringBeginWithSpace(" nizar"),is(true));
        assertThat(UserInputVerification.isStringBeginWithSpace("n izar"),is(false));
        assertThat(UserInputVerification.isStringBeginWithSpace("nizar "),is(false));
        assertThat(UserInputVerification.isStringBeginWithSpace("ni zar"),is(false));
        assertThat(UserInputVerification.isStringBeginWithSpace(" /fdspsùdl"),is(true));
    }

    @Test
    public void isStringSuperiorThenN(){
        assertThat(UserInputVerification.isStringSuperiorThenN("nizar",(byte)4),is(true));
        assertThat(UserInputVerification.isStringSuperiorThenN("abdelhedi",(byte)7),is(true));
        assertThat(UserInputVerification.isStringSuperiorThenN("sdlk",(byte)8),is(false));
        assertThat(UserInputVerification.isStringSuperiorThenN("talQuest",(byte)4),is(true));
        assertThat(UserInputVerification.isStringSuperiorThenN("nizar",(byte)4),is(true));
    }




}