package com.poc.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesOwner {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
}
