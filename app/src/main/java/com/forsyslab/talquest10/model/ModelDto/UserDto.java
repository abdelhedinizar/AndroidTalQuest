package com.forsyslab.talquest10.model.ModelDto;

import com.forsyslab.talquest10.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abdelhedi on 25/05/2017.
 */

public class UserDto {

    @SerializedName("email")
    protected String email;
    @SerializedName("firstName")
    protected String firstName;
    @SerializedName("lastName")
    protected String lastName;

    @SerializedName("login")
    protected String login;
    @SerializedName("password")
    protected String password;

    @SerializedName("country")
    protected String country;

    @SerializedName("postcode")
    protected String postcode;


    @SerializedName("logo")
    private String logo;
    @SerializedName("cover")
    private String cover;

    @SerializedName("company")
    protected String company;
    @SerializedName("jobTitle")
    protected String jobTitle;
    @SerializedName("sector")
    protected String sector;
    @SerializedName("type")
    protected String type;

    private String description;
    private String createdDate;
    protected String noEmp;
    private String projectsDescription;
    private String partners;
    private String hq;
    private String website;
    private String fbAccount;
    private String twitterAccount;
    private String googleAccount;
    private String linkedinAccount;



    public User changeToUser(){
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setCountry(country);
        user.setPostcode(postcode);
        user.setLogo(logo);
        user.setCover(cover);
        user.setCompany(company);
        user.setJobTitle(jobTitle);
        user.setSector(sector);
        user.setType(type);
        return user;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFbAccount() {
        return fbAccount;
    }

    public void setFbAccount(String fbAccount) {
        this.fbAccount = fbAccount;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
    }

    public String getLinkedinAccount() {
        return linkedinAccount;
    }

    public void setLinkedinAccount(String linkedinAccount) {
        this.linkedinAccount = linkedinAccount;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getNoEmp() {
        return noEmp;
    }

    public void setNoEmp(String noEmp) {
        this.noEmp = noEmp;
    }

    public String getProjectsDescription() {
        return projectsDescription;
    }

    public void setProjectsDescription(String projectsDescription) {
        this.projectsDescription = projectsDescription;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public UserDto(String email, String firstName, String lastName,String type) {
        this.type = type;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public UserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    /*

    protected String noEmp;
    private String description;
    private String hq;
    private String website;
    private String dateCreated;
    private String skills;
    private String experience;
    private String companyType;
    private String fbAccount;
    private String twitterAccount;
    private String googleAccount;
    private String linkedinAccount;
    private String projectsDescription;
    private String partners;
    private String companyPage_role;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
*/
}
