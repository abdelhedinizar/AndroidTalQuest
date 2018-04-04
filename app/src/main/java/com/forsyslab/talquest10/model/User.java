package com.forsyslab.talquest10.model;

/video-2on43cd/attractive_teen_brunette_gets_pussy#_tabComments
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by nizar on 19/01/2017.
 */

public class User {
    protected String id;
    protected String login;
    @SerializedName("firstName")
    protected String firstName;
    protected String email;
    @SerializedName("lastName")
    protected String lastName;
    protected boolean activated;
    protected String langKey;
    protected ArrayList<String> authorities;
    protected String country;
    protected String postcode;
    @SerializedName("jobTitle")
    protected String jobTitle;
    protected String company;
    protected String sector;
    protected String type;
    @SerializedName("noEmp")
    protected String noEmp;
    private String description;
    private String hq;
    private String dateCreated;
    private Set<String> skills;
    private String experience;
    private String companyType;
    private String website;
    @SerializedName("fbAccount")
    private String fbAccount;
    @SerializedName("twitterAccount")
    private String twitterAccount;
    @SerializedName("googleAccount")
    private String googleAccount;
    @SerializedName("linkedinAccount")
    private String linkedinAccount;
    private String logo;
    private String cover;
    private String projectsDescription;
    private String partners;
    private String companyPage_role;
    private String solde;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    protected String password;

    public User(String firstName, String lastName, String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public User() {

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isActivated() {
        return activated;
    }

    public String getCompanyPage_role() {
        return companyPage_role;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public void setCompanyPage_role(String companyPage_role) {
        this.companyPage_role = companyPage_role;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }


    public String getLangKey() {
        return this.langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public ArrayList<String> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }


    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getSector() {
        return this.sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNoEmp() {
        return this.noEmp;
    }

    public void setNoEmp(String noEmp) {
        this.noEmp = noEmp;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getHq() {
        return this.hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }


    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }


    public Set<String> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }


    public String getExperience() {
        return this.experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    public String getCompanyType() {
        return this.companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }


    public String getFbAccount() {
        return this.fbAccount;
    }

    public void setFbAccount(String fbAccount) {
        this.fbAccount = fbAccount;
    }


    public String getTwitterAccount() {
        return this.twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }


    public String getGoogleAccount() {
        return this.googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
    }


    public String getLinkedinAccount() {
        return this.linkedinAccount;
    }

    public void setLinkedinAccount(String linkedinAccount) {
        this.linkedinAccount = linkedinAccount;
    }


    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public String getProjectsDescription() {
        return this.projectsDescription;
    }

    public void setProjectsDescription(String projectsDescription) {
        this.projectsDescription = projectsDescription;
    }


    public String getPartners() {
        return this.partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }


    public String getCompanyPageRole() {
        return this.companyPage_role;
    }

    public void setCompanyPageRole(String companyPage_role) {
        this.companyPage_role = companyPage_role;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
