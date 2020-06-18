package com.example.drpet.Model;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
public class Profiles extends RealmObject {
    @PrimaryKey
    private int profile_id;
    private String profile_fname;
    private String profile_lname;
    private String profile_email;
    private int profile_phone;

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_fname() {
        return profile_fname;
    }

    public void setProfile_fname(String profile_fname) {
        this.profile_fname = profile_fname;
    }

    public String getProfile_lname() {
        return profile_lname;
    }

    public void setProfile_lname(String profile_lname) {
        this.profile_lname = profile_lname;
    }

    public String getProfile_email() {
        return profile_email;
    }

    public void setProfile_email(String profile_email) {
        this.profile_email = profile_email;
    }

    public int getProfile_phone() {
        return profile_phone;
    }

    public void setProfile_phone(int profile_phone) {
        this.profile_phone = profile_phone;
    }
}
