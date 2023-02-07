package com.poc.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.constant.PhoneType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Phone {
    private String phone;
    private PhoneType phoneType;
    private String countryCode;
}
