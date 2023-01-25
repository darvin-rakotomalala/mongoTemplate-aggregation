package com.poc.service.application;

import com.poc.mapper.ContactMapper;
import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import com.poc.model.dto.ContactDTO;
import com.poc.repository.ContactCustomRepository;
import com.poc.utils.HelpPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactRSAImpl implements ContactRSA {

    private final ContactCustomRepository contactCustomRepository;
    private final ContactMapper contactMapper;

    @Override
    public List<ContactDTO> getAllContacts() {
        try {
            log.info("----- getAllContacts");
            return contactMapper.toDTO(contactCustomRepository.findAllContacts());
        } catch (Exception e) {
            log.error("Error getAllContacts : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactDTO> matchContactsBySource(Source source) {
        try {
            log.info("----- matchContactsBySource : {}", source);
            return contactMapper.toDTO(contactCustomRepository.matchContactsBySource(source));
        } catch (Exception e) {
            log.error("Error matchContactsBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactDTO> projectContactsBySource(Source source) {
        try {
            log.info("----- projectContactsBySource : {}", source);
            return contactMapper.toDTO(contactCustomRepository.projectContactsBySource(source));
        } catch (Exception e) {
            log.error("Error projectContactsBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactInfoAggregate> countContactsBySource() {
        try {
            log.info("----- countContactsBySource");
            return contactCustomRepository.countContactsBySource();
        } catch (Exception e) {
            log.error("Error countContactsBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactDTO> getDistinctSourceValues() {
        try {
            log.info("----- getDistinctSourceValues");
            return contactMapper.toDTO(contactCustomRepository.findDistinctSourceValues());
        } catch (Exception e) {
            log.error("Error getDistinctSourceValues : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactInfoAggregate> groupContactsBySource() {
        try {
            log.info("----- groupContactsBySource");
            return contactCustomRepository.groupContactsBySource();
        } catch (Exception e) {
            log.error("Error groupContactsBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactDTO> getContactsBySourceAndLimit(Source source, long maxDocuments) {
        try {
            log.info("----- getContactsBySourceAndLimit : {} : {}", source, maxDocuments);
            return contactMapper.toDTO(contactCustomRepository.findContactsBySourceAndLimit(source, maxDocuments));
        } catch (Exception e) {
            log.error("Error saveAllTutorials : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ContactDTO> getContactsBySourceAndSkip(Source source, long elementsToSkip) {
        try {
            log.info("----- getContactsBySourceAndSkip : {} : {}", source, elementsToSkip);
            return contactMapper.toDTO(contactCustomRepository.findContactsBySourceAndSkip(source, elementsToSkip));
        } catch (Exception e) {
            log.error("Error getContactsBySourceAndSkip : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SaleInfoAggregate> getTotalSalesPerContact() {
        try {
            log.info("----- getTotalSalesPerContact");
            return contactCustomRepository.getTotalSalesPerContact();
        } catch (Exception e) {
            log.error("Error getTotalSalesPerContact : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Sale> listContactSales() {
        try {
            log.info("----- listContactSales");
            return contactCustomRepository.listContactSales();
        } catch (Exception e) {
            log.error("Error listContactSales : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SalesOwner> getSalesOwnersBySource(Source source) {
        try {
            log.info("----- getSalesOwnersBySource: {}", source);
            return contactCustomRepository.findSalesOwnersBySource(source);
        } catch (Exception e) {
            log.error("Error getSalesOwnersBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SalesOwner> groupSalesOwnersBySource(Source source) {
        try {
            log.info("----- groupSalesOwnersBySource : {}", source);
            return contactCustomRepository.groupSalesOwnersBySource(source);
        } catch (Exception e) {
            log.error("Error groupSalesOwnersBySource : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public HelpPage<ContactDTO> getContactsByProperties(String firstName, String lastName, String email, String company, String title, Source source, Pageable pageable) {
        try {
            log.info("----- getContactsByProperties");
            return contactMapper.toDTO(contactCustomRepository.
                    findContactsByProperties(firstName, lastName, email, company, title, source, pageable), pageable);
        } catch (Exception e) {
            log.error("Error getContactsByProperties : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public HelpPage<ContactDTO> searchByName(String name, Pageable pageable) {
        try {
            log.info("----- searchByName : {}", name);
            return contactMapper.toDTO(contactCustomRepository.searchByName(name, pageable), pageable);
        } catch (Exception e) {
            log.error("Error searchByName : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SaleInfoAggregate> getBigSalesPerContact() {
        try {
            log.info("----- getBigSalesPerContact");
            return contactCustomRepository.getBigSalesPerContact();
        } catch (Exception e) {
            log.error("Error getBigSalesPerContact : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SaleInfoAggregate> getLobSalesByContact() {
        try {
            log.info("----- getLobSalesByContact");
            return contactCustomRepository.findLobSalesByContact();
        } catch (Exception e) {
            log.error("Error getLobSalesByContact : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SaleInfoAggregate> getFirstSaleForEachContact() {
        try {
            log.info("----- getFirstSaleForEachContact");
            return contactCustomRepository.findFirstSaleForEachContact();
        } catch (Exception e) {
            log.error("Error getFirstSaleForEachContact : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

}
