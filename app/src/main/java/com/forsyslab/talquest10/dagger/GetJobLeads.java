package com.forsyslab.talquest10.dagger;

/**
 * Created by LENOVO on 27/03/2017.
 */


import com.forsyslab.talquest10.model.JobLeads;
import com.forsyslab.talquest10.model.ModelDto.JobLeadsDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by LENOVO on 23/03/2017.
 */

public interface GetJobLeads {

    @GET("/api/job-leads")
    Call<List<JobLeads>> getJobLeads(@Header(AUTHORIZATION) String authHeader);

}
