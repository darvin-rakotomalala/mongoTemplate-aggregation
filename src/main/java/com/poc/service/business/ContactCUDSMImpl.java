package com.poc.service.business;

import com.poc.model.domain.Contact;
import com.poc.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactCUDSMImpl implements ContactCUDSM {

    private final ContactRepository contactRepository;

    @Override
    public List<Contact> saveAllContacts(List<Contact> contacts) {
        try {
            log.info("----- saveAllContacts");
            return contactRepository.saveAll(contacts);
        } catch (Exception e) {
            log.error("Error saveAllContacts : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

}
