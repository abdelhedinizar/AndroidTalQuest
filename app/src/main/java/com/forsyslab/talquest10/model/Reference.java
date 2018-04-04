package com.forsyslab.talquest10.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abdelhedi on 22/05/2017.
 */

public class Reference {
    private String id;
    @SerializedName("referredLogin")
    private String referredLogin;
    @SerializedName("referredByLogin")
    private String referredByLogin;
    @SerializedName("accepted")
    private Boolean accepted;


    public Reference(String id, String referredLogin, String referredByLogin) {
        this.id = id;
        this.referredLogin = referredLogin;
        this.referredByLogin = referredByLogin;
    }

    public Reference(String referredLogin, String referredByLogin) {
        this.referredLogin = referredLogin;
        this.referredByLogin = referredByLogin;
    }

    public Reference() {
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferredLogin() {
        return referredLogin;
    }

    public void setReferredLogin(String referredLogin) {
        this.referredLogin = referredLogin;
    }

    public String getReferredByLogin() {
        return referredByLogin;
    }

    public void setReferredByLogin(String referredByLogin) {
        this.referredByLogin = referredByLogin;
    }
}
