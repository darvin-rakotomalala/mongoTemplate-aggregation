package com.poc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMongoRepositoryImpl implements CustomMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Object executMongoRequest(String request) {
        return mongoTemplate.executeCommand(request);
    }
}
