package com.poc.service.application;

import com.poc.mapper.ContactMapper;
import com.poc.model.dto.ContactDTO;
import com.poc.service.business.ContactCUDSM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ContactCUDSAImpl implements ContactCUDSA {

    private final ContactCUDSM contactCUDSM;
    private final ContactMapper contactMapper;

    @Override
    public List<ContactDTO> saveAllContacts(List<ContactDTO> contacts) {
        List<ContactDTO> newContacts = new ArrayList<>();
        contacts.forEach(contact -> {
            contact.setId(UUID.randomUUID());
            newContacts.add(contact);
        });
        return contactMapper.toDTO(contactCUDSM.saveAllContacts(contactMapper.toDO(newContacts)));
    }

}
