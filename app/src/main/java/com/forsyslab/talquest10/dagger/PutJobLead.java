package com.forsyslab.talquest10.dagger;

import com.forsyslab.talquest10.model.JobLeads;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

import static com.forsyslab.talquest10.constant.Const.AUTHORIZATION;

/**
 * Created by abdelhedi on 02/06/2017.
 */

public interface PutJobLead {
    @PUT("/api/job-leads")
    Call<JobLeads> putJobLeads(@Header(AUTHORIZATION)String authHeader, @Body JobLeads jobLeads);

}
