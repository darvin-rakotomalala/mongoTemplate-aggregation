package com.poc.model.aggregate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.constant.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ContactInfoAggregate {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Source source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long count;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> contacts;
}
