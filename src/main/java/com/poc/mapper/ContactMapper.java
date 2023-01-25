package com.poc.mapper;

import com.poc.common.mapper.DtoMapper;
import com.poc.model.domain.Contact;
import com.poc.model.dto.ContactDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper extends DtoMapper<ContactDTO, Contact> {

}
