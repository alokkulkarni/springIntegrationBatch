package com.example.alok;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by alokkulkarni on 13/02/17.
 */

@Entity
@Table(name = "USERDETAILS")
@JsonAutoDetect
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;
    @Column(name = "FIRSTNAME")
    @JsonProperty("firstName")
    private String firstName;
    @Column(name = "LASTNAME")
    @JsonProperty("lastName")
    private String lastName;


    public UserDetails() {
    }


    //    @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
    public UserDetails(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

//    public UserDetails(Long id, String firstName, String lastName) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
