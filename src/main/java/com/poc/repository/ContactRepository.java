package com.poc.repository;

import com.poc.model.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ContactRepository extends MongoRepository<Contact, UUID> {

}
