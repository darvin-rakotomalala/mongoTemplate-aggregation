package com.poc.service.business;

import com.poc.model.domain.Contact;

import java.util.List;

public interface ContactCUDSM {
    public List<Contact> saveAllContacts(List<Contact> contacts);
}
