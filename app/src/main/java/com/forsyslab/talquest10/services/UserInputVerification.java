package com.forsyslab.talquest10.services;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nizar on 25/01/2017.
 */

public class UserInputVerification {

    private UserInputVerification() {
    }

    public static boolean isWebsiteValid(String potentialUrl) {
        return Patterns.WEB_URL.matcher(potentialUrl).matches();
    }

    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();

    }

    public static boolean isPostCodeValid(String postCode, String regExpn) {

        CharSequence inputStr = postCode;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        return false;
    }

    public static boolean isEditTextEmpty(EditText editText, String title) {

        if (isEmpty(editText.getText().toString())) {
            editText.setError("please write your " + title);
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String srt) {
        return "".equals(srt);
    }

    public static boolean isEditTextSuperiorThenN(EditText editText, Byte n, String title) {
        if (isStringSuperiorThenN(editText.getText().toString(), n)) {
            editText.setError(title + " must contain at most " + n + " letters");
            return true;
        }

        return false;
    }

    public static boolean isStringSuperiorThenN(String str, Byte n) {
        return str.length() > n;
    }


    public static boolean isEditTextInferiorThenN(EditText editText, Byte n, String title) {
        if (editText.getText().length() < n) {
            editText.setError(title + " must contain at least " + n + " letters");
            return true;
        }
        return false;
    }


    public static boolean isEditTextBeginWithSpace(EditText editText) {
        if (isStringBeginWithSpace(editText.getText().toString())) {
            editText.setError("The first character must not be a space");
            return true;
        }
        return false;
    }

    public static boolean isStringBeginWithSpace(String str) {
        return str.charAt(0) == ' ';
    }

    public static boolean isEditTextHasEspace(EditText editText) {
        if (isStrHasEspace(editText.getText().toString())) {
            editText.setError("No space");
            return true;
        }
        return false;
    }

    public static boolean isStrHasEspace(String str) {
        return str.indexOf(' ') != -1;
    }

    public static boolean isEditTextHasChiffre(EditText editText) {
        if (isStrHasChiffre(editText.getText().toString())) {
            editText.setError("your input has chiffre");
            return true;

        }
        return false;
    }

    public static boolean isStrHasChiffre(String str) {
        return str.length() != str.replaceAll("[*0-9]", "").length();
    }

    public static void showTextViewComparedToLenght(TextView textView) {
        if (textView.length() > 23) {
            StringBuilder jobOutput = new StringBuilder(textView.getText()).replace(23, textView.getText().length(), "");
            textView.setText(jobOutput + "...");
        }
    }

    public static String showTextViewComparedToLenght(String str,int lenght) {
        if (str.length() > lenght) {
            StringBuilder jobOutput = new StringBuilder(str).replace(lenght, str.length(), "");
            return (jobOutput + "...");
        }
        else return str;
    }

}