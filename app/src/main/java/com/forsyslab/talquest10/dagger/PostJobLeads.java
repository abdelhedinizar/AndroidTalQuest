package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.ModelDto.JobLeadsDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by LENOVO on 12/04/2017.
 */

public interface PostJobLeads {
@POST("/api/job-leads")
    Call<JobLeadsDto> postJobLeads(@Header(AUTHORIZATION)String authHeader,@Body JobLeadsDto jobLeadsDto);

}
