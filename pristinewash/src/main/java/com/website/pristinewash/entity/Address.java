package com.website.pristinewash.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;

    private String city;

    private String zipCode;

    // Define the relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Other fields and relationships as needed
}

