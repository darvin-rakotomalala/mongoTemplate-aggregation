package com.poc.repository.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class MongoRepositoryUtils {

    public static <T> Page<T> search(Criteria criteria, String collection, Class<T> clazz, Pageable pageable, MongoTemplate template) {
        Query query = new Query()
                .addCriteria(criteria);

        List<T> filteredZones = template.find(query.with(pageable), clazz, collection);

        Query queryBis = new Query()
                .addCriteria(criteria);
        Page<T> pageRes = PageableExecutionUtils.getPage(
                filteredZones,
                pageable,
                () -> template.count(queryBis, clazz));
        return pageRes;
    }

}
