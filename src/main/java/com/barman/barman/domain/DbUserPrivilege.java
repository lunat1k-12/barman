package com.barman.barman.domain;

import com.barman.barman.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "USER_PRIVILEGES")
public class DbUserPrivilege {

    public DbUserPrivilege() {}

    public DbUserPrivilege(String userId) {
        this.userId = userId;
        this.allowAllPhoto = Constants.N;
        this.allowPhoto = Constants.N;
        this.allowDrink = Constants.N;
    }

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ALLOW_DRINK")
    private String allowDrink;

    @Column(name = "ALLOW_PHOTO")
    private String allowPhoto;

    @Column(name = "ALLOW_ALL_PHOTO")
    private String allowAllPhoto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllowDrink() {
        return allowDrink;
    }

    public void setAllowDrink(String allowDrink) {
        this.allowDrink = allowDrink;
    }

    public String getAllowPhoto() {
        return allowPhoto;
    }

    public void setAllowPhoto(String allowPhoto) {
        this.allowPhoto = allowPhoto;
    }

    public String getAllowAllPhoto() {
        return allowAllPhoto;
    }

    public void setAllowAllPhoto(String allowAllPhoto) {
        this.allowAllPhoto = allowAllPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbUserPrivilege that = (DbUserPrivilege) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(allowDrink, that.allowDrink) &&
                Objects.equals(allowPhoto, that.allowPhoto) &&
                Objects.equals(allowAllPhoto, that.allowAllPhoto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, allowDrink, allowPhoto, allowAllPhoto);
    }
}
