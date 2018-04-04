package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.ModelDto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by abdelhedi on 05/07/2017.
 */

public interface GetAllProfilWithoutImage {
    @GET("/api/usersWithoutImage")
    Call<List<UserDto>> getUsers(@Header(AUTHORIZATION) String authHeader);
}
