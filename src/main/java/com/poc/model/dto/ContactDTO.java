package com.poc.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.constant.ContactStatus;
import com.poc.model.constant.LineOfBusiness;
import com.poc.model.constant.Source;
import com.poc.model.domain.Address;
import com.poc.model.domain.Phone;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ContactDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Phone> phones;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Address> addresses;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Source source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sourceDetails;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContactStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long statusChange;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<LineOfBusiness> linesOfBusiness;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String company;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean authority;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SalesOwner salesOwner;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Sale> sales;
}
