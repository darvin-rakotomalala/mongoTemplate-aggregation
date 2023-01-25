package com.poc.model.aggregate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.model.domain.Sale;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SaleInfoAggregate {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Sale sale;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalSales;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer bigSales;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> lobs = new ArrayList<>();
}
