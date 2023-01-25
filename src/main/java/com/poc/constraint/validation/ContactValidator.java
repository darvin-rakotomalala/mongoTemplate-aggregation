package com.poc.constraint.validation;

import com.poc.exception.ErrorsEnum;
import com.poc.model.dto.ContactDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ContactDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContactDTO contact = (ContactDTO) target;
        if (StringUtils.isBlank(contact.getFirstName())) {
            errors.rejectValue("lastName", "contact.lastName.empty", ErrorsEnum.ERR_MCS_CONTACT_LASTNAME_EMPTY.getErrorMessage());
        }
        if (StringUtils.isBlank(contact.getFirstName())) {
            errors.rejectValue("firstName", "contact.firstName.empty", ErrorsEnum.ERR_MCS_CONTACT_FIRSTNAME_EMPTY.getErrorMessage());
        }
    }

}
