package com.forsyslab.talquest10.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.forsyslab.talquest10.model.Company;
import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.PersonalProfil;

import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_COMPANY_DESCRIPTION;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_JOBTITLE;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_MISSION_DESCRIPTION;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_PROFIL_DESCRIPTION;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_SALARY;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_SECTOR;
import static com.forsyslab.talquest10.constant.Const.JOB_LEADS_TITLE;
import static com.forsyslab.talquest10.constant.Const.JSON_COMPANY;
import static com.forsyslab.talquest10.constant.Const.JSON_COUNTRY;
import static com.forsyslab.talquest10.constant.Const.JSON_CREATED_DATE;
import static com.forsyslab.talquest10.constant.Const.JSON_DESCRIPTION;
import static com.forsyslab.talquest10.constant.Const.JSON_EMAIL;
import static com.forsyslab.talquest10.constant.Const.JSON_FBACCOUNT;
import static com.forsyslab.talquest10.constant.Const.JSON_FIRST_NAME;
import static com.forsyslab.talquest10.constant.Const.JSON_GOOGLEACCOUNT;
import static com.forsyslab.talquest10.constant.Const.JSON_HQ;
import static com.forsyslab.talquest10.constant.Const.JSON_JOB_TITLE;
import static com.forsyslab.talquest10.constant.Const.JSON_LAST_MODIFIED_DATE;
import static com.forsyslab.talquest10.constant.Const.JSON_LAST_NAME;
import static com.forsyslab.talquest10.constant.Const.JSON_NOEMP;
import static com.forsyslab.talquest10.constant.Const.JSON_PARTNERS;
import static com.forsyslab.talquest10.constant.Const.JSON_POSTCODE;
import static com.forsyslab.talquest10.constant.Const.JSON_PROJECTSDESCRIPTION;
import static com.forsyslab.talquest10.constant.Const.JSON_SECTOR;
import static com.forsyslab.talquest10.constant.Const.JSON_TWITTRT_ACCOUNT;
import static com.forsyslab.talquest10.constant.Const.JSON_USERNAME;
import static com.forsyslab.talquest10.constant.Const.JSON_WEBSITE;
import static com.forsyslab.talquest10.constant.Const.JSON_lINKED_IN;
import static com.forsyslab.talquest10.constant.Const.TAG;

/**
 * Created by LENOVO on 07/02/2017.
 */

public class SQLiteService {

    private static final String SQL_TEXT = " TEXT,";
    private static final String SQL_TEXT_PARENTHESE =" TEXT);";

    public static final String PERSON_PROFIL_REQUETE = "CREATE TABLE IF NOT EXISTS PERSONALPROFIL1 (" + JSON_USERNAME + SQL_TEXT + JSON_FIRST_NAME + SQL_TEXT + JSON_LAST_NAME + SQL_TEXT + JSON_COUNTRY + SQL_TEXT
            + JSON_POSTCODE + SQL_TEXT + JSON_JOB_TITLE + SQL_TEXT + JSON_SECTOR + SQL_TEXT + JSON_COMPANY + SQL_TEXT + JSON_LAST_MODIFIED_DATE + SQL_TEXT_PARENTHESE;
    public static final String COMPANY_REQUETE = "CREATE TABLE IF NOT EXISTS COMPANY (" + JSON_USERNAME + SQL_TEXT + JSON_EMAIL + SQL_TEXT + JSON_COUNTRY + SQL_TEXT + JSON_POSTCODE + SQL_TEXT
            + JSON_NOEMP + SQL_TEXT + JSON_DESCRIPTION + SQL_TEXT + JSON_HQ + SQL_TEXT + JSON_WEBSITE + SQL_TEXT + JSON_CREATED_DATE + SQL_TEXT + JSON_FBACCOUNT + SQL_TEXT +
            JSON_TWITTRT_ACCOUNT + SQL_TEXT + JSON_GOOGLEACCOUNT + SQL_TEXT + JSON_lINKED_IN + SQL_TEXT + JSON_PROJECTSDESCRIPTION + SQL_TEXT + JSON_PARTNERS + SQL_TEXT_PARENTHESE;
    public static final String TIME_LINE = "CREATE TABLE IF NOT EXISTS JOBTIMELINE (" + JOB_LEADS_TITLE +
            SQL_TEXT + JOB_LEADS_SALARY + SQL_TEXT + JOB_LEADS_JOBTITLE + SQL_TEXT + JOB_LEADS_SECTOR + SQL_TEXT
            + JOB_LEADS_COMPANY_DESCRIPTION + SQL_TEXT + JOB_LEADS_PROFIL_DESCRIPTION + SQL_TEXT + JOB_LEADS_MISSION_DESCRIPTION + SQL_TEXT_PARENTHESE;


    public SQLiteDatabase db;


    public SQLiteService(Context context) {
        db = context.openOrCreateDatabase("talquest", context.MODE_PRIVATE, null);
    }

/*
    public void createPersonProfilTable() {
        db.execSQL(PERSON_PROFIL_REQUETE);
        Log.d("nizar", PERSON_PROFIL_REQUETE);
    }
    public void createTimeLineTable() {
        db.execSQL(TIME_LINE);
        Log.d("nizar", TIME_LINE);

    }
*/

    public void createCompanyTable() {
        db.execSQL(COMPANY_REQUETE);
    }

    public void insertTimeLine(JobLeads jobLeads) {
        String insertTimeLine = "INSERT INTO JOBTIMELINE VALUES ('" + jobLeads.getTitle() + "','" + jobLeads.getSalary() + "','" +
                jobLeads.getTitle() + "','" + jobLeads.getSector() + "','" + jobLeads.getCompanyDesc() + "','" + jobLeads.getProfileSearch() + "','" + /*jobLeads.getMissionDesc() +
*/                "')";
        Log.d(TAG,insertTimeLine);
        db.execSQL(insertTimeLine);
    }

    public void insertPersonalProfilInTable(PersonalProfil profil) {
        String insertProfil = "INSERT INTO PERSONALPROFIL1 VALUES ('" + profil.getUserName() + "','" + profil.getFirstName() + "','" + profil.getLastName() + "','" + profil.getCountry() + "','" + profil.getPostcode() + "','" + profil.getJobTitle() + "','" + profil.getSector() + "','" + profil.getCompany() + "','" + profil.getLastModifiedDate() + "')";
        Cursor cursor = db.rawQuery("SELECT * FROM PERSONALPROFIL WHERE " + JSON_USERNAME + "='" + profil.getUserName() + "'", null);
        int lastModifiedCI = cursor.getColumnIndex(JSON_LAST_MODIFIED_DATE);
        cursor.moveToFirst();

        String nizarTalquest = "nizarTalquest";
        if (cursor.getCount() == 0) {
            db.execSQL(insertProfil);
            Log.d(nizarTalquest, insertProfil);
        } else if (!cursor.getString(lastModifiedCI).equals(profil.getLastModifiedDate())) {
            db.execSQL(insertProfil);
            Log.d(nizarTalquest, "DataBaseModified");
        } else {
            Log.d(nizarTalquest, "DataBase Not Modified");
        }
    }

    public void insertCompanyInTable(Company company) {
        String insertCompany = "INSERT INTO COMPANY VALUES ('" + company.getUserName() + "','" + company.getEmail() + "','" + company.getCountry() + "','" + company.getPostCode() + "','" + company.getNumberOfEmployees() + "','" +
                company.getCompanyDescription() + "','" + company.getHeadQuarter() + "','" + company.getWebSite() + "','" + company.getCreationDate() + "','" + company.getFacebookAccount() + "','" + company.getTwitterAccount() + "','" +
                company.getGoogleAccount() + "','" + company.getLinkedinAccount() + "','" + company.getProjetDescription() + "','" + company.getPartners() + "')";

        Cursor cursor = db.rawQuery("SELECT * FROM COMPANY WHERE " + JSON_USERNAME + "='" + company.getUserName() + "'", null);
        if (cursor.getCount() == 0) {
            db.execSQL(insertCompany);
        }
        Log.d("nizarTalquest", insertCompany);

    }

}
