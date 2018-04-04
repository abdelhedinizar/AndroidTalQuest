package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.PersonalProfil;
import com.forsyslab.talquest10.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by LENOVO on 28/03/2017.
 */

public interface GetProfil {
    @GET("/api/users/{user}")
    Call<User> getUser(@Path("user")String user, @Header(AUTHORIZATION) String authHeader);
}
