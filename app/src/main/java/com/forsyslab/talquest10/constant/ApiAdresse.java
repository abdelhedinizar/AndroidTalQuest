package com.forsyslab.talquest10.constant;


/**
 * Created by nizar on 25/01/2017.
 */

public class ApiAdresse {
    public static final String HTTP ="http://";
    public static final String IP ="192.168.1.7:8080";
    public static final String LOGIN_API = HTTP+IP+"/api/authenticate";
    public static final String SIGN_UP_API = HTTP+IP+"/api/register";
    public static final String PROFILS_API =HTTP+IP+"/api/users";
    public static final String JOB_LEADS_API =HTTP+IP+"/api/job-leads";
    public static final String JOB_LEADS_API2 =HTTP+IP;
    private ApiAdresse() {
    }
}
