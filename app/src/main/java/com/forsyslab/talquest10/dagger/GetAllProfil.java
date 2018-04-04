package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by abdelhedi on 22/05/2017.
 */
public interface GetAllProfil {
    @GET("/api/users")
    Call<List<User>> getUsers(@Header(AUTHORIZATION) String authHeader);
}
