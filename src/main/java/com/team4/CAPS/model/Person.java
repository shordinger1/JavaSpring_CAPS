package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    private String firstname;

    private String lastname;

    private String gender;
    @Temporal(TemporalType.DATE)
    private Integer birthday;

    @Column(unique = true)
    private String username;

    private String password;

    private String surname;

    private String email;

    private String address;

    private String contactNumber;

    public String getFullName()
    {
        return firstname+" "+lastname;
    }

}
