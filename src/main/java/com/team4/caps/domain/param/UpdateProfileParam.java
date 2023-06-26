package com.team4.caps.domain.param;

import lombok.Data;

@Data
public class UpdateProfileParam {
    private String firstName;

    private String lastName;

    private Long birthday;

    private String email;

    private String address;

    private String contactNumber;
}
