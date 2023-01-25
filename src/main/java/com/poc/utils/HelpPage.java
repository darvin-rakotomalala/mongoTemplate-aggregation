package com.poc.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HelpPage<T> extends PageImpl<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public HelpPage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number,
                    @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements,
                    @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                    @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort,
                    @JsonProperty("first") boolean first, @JsonProperty("empty") boolean empty,
                    @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public HelpPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public HelpPage(List<T> content) {
        super(content);
    }

    public HelpPage() {
        super(new ArrayList<>());
    }
}
