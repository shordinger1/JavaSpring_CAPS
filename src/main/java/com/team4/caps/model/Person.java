package com.team4.caps.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@MappedSuperclass
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Person() {

    }

    @NotBlank(message = "Name is required")
    private String firstname;

    @NotBlank(message = "Name is required")
    private String lastname;

    private String gender;
    @Temporal(TemporalType.DATE)
    private Long birthday;

    //@Column(unique = true)
    private String username;

    private String password;

    private String surname;
    @NotBlank(message = "email is required")
    @Email(message = "please provide valid email address")
    private String email;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "contactnumber is required")

    private String contactNumber;

    public String getFullName() {
        return firstname + " " + lastname;
    }
}