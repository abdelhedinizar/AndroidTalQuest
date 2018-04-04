package com.forsyslab.talquest10.model;


/**
 * Created by nizar on 19/01/2017.
 */

public class Company extends User {

    private String userName;
    private String password;
    private String country;
    private String postCode;
    private String numberOfEmployees; //add
    private String companyDescription;
    private String headQuarter;  //add
    private String webSite;      //add
    private String creationDate;  //add
    private String facebookAccount;
    private String twitterAccount;
    private String googleAccount;
    private String linkedinAccount;
    private String projetDescription; //add
    private String logo;
    private String coverPhoto;
    private String partners;

    public Company(String userName, String eMail, String password) {
        this.userName = userName;
        this.email = eMail;
        this.password = password;
    }
    public Company(){}


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(String numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getHeadQuarter() {
        return headQuarter;
    }

    public void setHeadQuarter(String headQuarter) {
        this.headQuarter = headQuarter;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
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

    public String getProjetDescription() {
        return projetDescription;
    }

    public void setProjetDescription(String projetDescription) {
        this.projetDescription = projetDescription;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }


}
