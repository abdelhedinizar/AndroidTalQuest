package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.ModelDto.UserDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by LENOVO on 10/04/2017.
 */

public interface Register {
    @Headers("Accept: application/json")
    @POST("/api/register")
    Call<ResponseBody> register(@Body UserDto user);
}
