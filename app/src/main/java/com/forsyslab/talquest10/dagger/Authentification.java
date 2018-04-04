package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.Authenticate;
import com.forsyslab.talquest10.model.IdToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LENOVO on 30/03/2017.
 */

public interface Authentification {

    @POST("/api/authenticate")
    Call<IdToken> getIdToken(@Body Authenticate authenticate);
}
