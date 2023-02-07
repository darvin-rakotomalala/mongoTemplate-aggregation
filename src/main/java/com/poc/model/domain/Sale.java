package com.poc.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.constant.LineOfBusiness;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Sale {
    private String purchaseOrderNumber;
    private String title;
    private Integer value;
    private Date date;
    private LineOfBusiness lineOfBusiness;
}
