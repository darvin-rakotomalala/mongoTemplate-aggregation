package com.poc.service.application;

import com.poc.model.dto.ContactDTO;

import java.util.List;

public interface ContactCUDSA {
    public List<ContactDTO> saveAllContacts(List<ContactDTO> contacts);
}
