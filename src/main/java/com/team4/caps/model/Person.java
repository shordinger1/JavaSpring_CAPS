package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    public Person() {

    }
    private String firstname="_";

    private String lastname="_";

    private String gender="_";
    @Temporal(TemporalType.DATE)
    private Long birthday= (long) -1;

    //@Column(unique = true)
    private String username="_";

    private String password="_";

    private String surname="_";

    private String email="_";

    private String address="_";

    private String contactNumber="_";

    public String getFullName()
    {
        return firstname+" "+lastname;
    }





}
