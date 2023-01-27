package com.poc.model.domain;

import com.poc.model.constant.ContactStatus;
import com.poc.model.constant.LineOfBusiness;
import com.poc.model.constant.Source;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contacts")
public class Contact {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Phone> phones;
    private List<Address> addresses;
    private Source source;
    private String sourceDetails;
    private ContactStatus status;
    private long statusChange;
    private List<LineOfBusiness> linesOfBusiness;
    private String company;
    private String title;
    private boolean authority;
    private SalesOwner salesOwner;
    private List<Sale> sales;
}
