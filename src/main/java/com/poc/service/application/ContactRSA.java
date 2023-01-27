package com.poc.service.application;

import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import com.poc.model.dto.ContactDTO;
import com.poc.model.dto.ResultUnique;
import com.poc.utils.HelpPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactRSA {
    public List<ContactDTO> getAllContacts();
    public HelpPage<ContactDTO> searchContactsByName(String name, Pageable pageable);
    public List<ContactDTO> matchContactsBySource(Source source);
    public List<ContactDTO> projectContactsBySource(Source source);
    public List<ContactInfoAggregate> countContactsBySource();
    public List<ResultUnique> getDistinctSourceValues();
    public List<ContactInfoAggregate> groupContactsBySource();
    public List<ContactDTO> getContactsBySourceAndLimit(Source source, long maxDocuments);
    public List<ContactDTO> getContactsBySourceAndSkip(Source source, long elementsToSkip);
    public List<SaleInfoAggregate> getTotalSalesPerContact();
    public List<Sale> listContactSales();
    public List<SalesOwner> getSalesOwnersBySource(Source source);
    public List<SalesOwner> groupSalesOwnersBySource(Source source);
    public HelpPage<ContactDTO> getContactsByProperties(String firstName, String lastName, String email, String company, String title, Source source, Pageable page);
    public HelpPage<ContactDTO> searchByName(String name, Pageable pageable);
    public List<SaleInfoAggregate> getBigSalesPerContact();
    public List<SaleInfoAggregate> getLobSalesByContact();
    public List<SaleInfoAggregate> getFirstSaleForEachContact();
}
