package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by abdelhedi on 16/06/2017.
 */

public interface PutProfil {
    @PUT("/api/users")
    Call<User> putProfil(@Header(AUTHORIZATION)String authHeader, @Body User user);
}
