package com.poc.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SalesOwner {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
}
