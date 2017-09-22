package org.huzaifa.ikleen;

import android.net.Uri;

/**
 * Created by Huzaifa on 14-Aug-17.
 */

public class UserInformation {

    private String userEmail, userName, userNumber, userAddress, userAddress2, userPincode;
    private String userImageURI;

    public UserInformation() {

    }

    public UserInformation(String email, String name, String number, String address, String address2, String pincode, String image) {
        this.userEmail = email;
        this.userName = name;
        this.userNumber = number;
        this.userAddress = address;
        this.userAddress2 = address2;
        this.userPincode = pincode;
        this.userImageURI = image;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserAddress2() {
        return userAddress2;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public String getUserImageURI() {
        return userImageURI;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserAddress2(String userAddress2) {
        this.userAddress2 = userAddress2;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public void setUserImageURI(String userImageURI) {
        this.userImageURI = userImageURI;
    }
}
