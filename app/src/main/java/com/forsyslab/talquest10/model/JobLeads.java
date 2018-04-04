package com.forsyslab.talquest10.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LENOVO on 17/02/2017.
 */

public class JobLeads {

    private String id;
    private String title;
    private String company_desc;
    private String profile_search;
    private String experience;
    private String ngo;
    private String salary;
    private String reward_default;
    private String reward_max;
    private String reward_min;
    private String reward_choosen;
    private String availability;
    private String sector;
    private String company_name;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    @SerializedName("referenceList")
    private Set<Reference> referenceList= new HashSet<>();


    public Set<Reference> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(Set<Reference> referenceList) {
        this.referenceList = referenceList;
    }


    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public String getCompanyDesc() { return this.company_desc; }

    public void setCompanyDesc(String company_desc) { this.company_desc = company_desc; }
/*
    private String mission_desc;

    public String getMissionDesc() { return this.mission_desc; }

    public void setMissionDesc(String mission_desc) { this.mission_desc = mission_desc; }
*/
    public String getProfileSearch() { return this.profile_search; }

    public void setProfileSearch(String profile_search) { this.profile_search = profile_search; }

    public String getExperience() { return this.experience; }

    public void setExperience(String experience) { this.experience = experience; }


    public String getNgo() { return this.ngo; }

    public void setNgo(String ngo) { this.ngo = ngo; }


    public String getSalary() { return this.salary; }

    public void setSalary(String salary) { this.salary = salary; }


    public String getRewardDefault() { return this.reward_default; }

    public void setRewardDefault(String reward_default) { this.reward_default = reward_default; }


    public String getRewardMax() { return this.reward_max; }

    public void setRewardMax(String reward_max) { this.reward_max = reward_max; }


    public String getRewardMin() { return this.reward_min; }

    public void setRewardMin(String reward_min) { this.reward_min = reward_min; }


    public String getRewardChoosen() { return this.reward_choosen; }

    public void setRewardChoosen(String reward_choosen) { this.reward_choosen = reward_choosen; }


    public String getAvailability() { return this.availability; }

    public void setAvailability(String availability) { this.availability = availability; }


    public String getSector() { return this.sector; }

    public void setSector(String sector) { this.sector = sector; }


    public String getCompanyName() { return this.company_name; }

    public void setCompanyName(String company_name) { this.company_name = company_name; }


    public String getCreatedBy() { return this.createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }


    public String getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }


    public String getLastModifiedBy() { return this.lastModifiedBy; }

    public void setLastModifiedBy(String lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }


    public String getLastModifiedDate() { return this.lastModifiedDate; }

    public void setLastModifiedDate(String lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }


 public JobLeads(String jobTitle, String title, String salary, String companyDescription, String sector, String profilDescription, String missionDescription) {
      //  this.jobTitle = jobTitle;
        this.title = title;
        this.salary = salary;
        this.company_desc = companyDescription;
        this.sector = sector;
        this.profile_search = profilDescription;
     //   this.mission_desc = missionDescription;
    }


}
