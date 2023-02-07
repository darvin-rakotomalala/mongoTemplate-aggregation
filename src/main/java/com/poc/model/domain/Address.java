package com.poc.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.constant.AddressType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private AddressType addressType;
}
