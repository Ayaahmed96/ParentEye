package com.example.parenteye;


import java.time.LocalDate;
import java.util.Date;

public class Community {
    private String CommunityId;
    private String Communityname;
    private String CommunityAbout;
    private String typeid;  //1 if group and 2 if page
    private LocalDate createdDate;
    private String coverPhotoId;
    private String PhotoId;
    private String AdminId;





    public Community(){

    }

    public Community(String communityId, String communityname, String communityAbout, String typeid, LocalDate createdDate, String coverPhotoId, String photoId, String adminId) {
        CommunityId = communityId;
        Communityname = communityname;
        CommunityAbout = communityAbout;
        this.typeid = typeid;
        this.createdDate = createdDate;
        this.coverPhotoId = coverPhotoId;
        PhotoId = photoId;
        AdminId = adminId;

    }

    public void setCommunityId(String communityId) {
        CommunityId = communityId;
    }

    public void setCommunityname(String communityname) {
        Communityname = communityname;
    }

    public void setCommunityAbout(String communityAbout) {
        CommunityAbout = communityAbout;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setCoverPhotoId(String coverPhotoId) {
        this.coverPhotoId = coverPhotoId;
    }

    public void setPhotoId(String photoId) {
        PhotoId = photoId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }




    public String getCommunityId() {
        return CommunityId;
    }

    public String getCommunityname() {
        return Communityname;
    }

    public String getCommunityAbout() {
        return CommunityAbout;
    }

    public String getTypeid() {
        return typeid;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getCoverPhotoId() {
        return coverPhotoId;
    }

    public String getPhotoId() {
        return PhotoId;
    }

    public String getAdminId() {
        return AdminId;
    }


}
