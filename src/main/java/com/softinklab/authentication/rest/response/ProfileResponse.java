package com.softinklab.authentication.rest.response;

import com.softinklab.rest.response.BaseResponse;
import lombok.Data;

@Data
public class ProfileResponse extends BaseResponse {
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String mobileNo;
    private String sex;
    private String prefix;
    private String vatNo;
    private String nicNo;
    private String companyName;
}
