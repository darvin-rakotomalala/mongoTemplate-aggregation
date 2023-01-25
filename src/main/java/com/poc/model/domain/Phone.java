package com.poc.model.domain;

import com.poc.model.constant.PhoneType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Phone {
    private String phone;
    private PhoneType phoneType;
    private String countryCode;
}
