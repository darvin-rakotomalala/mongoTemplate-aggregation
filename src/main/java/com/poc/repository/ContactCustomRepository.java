package com.poc.repository;

import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Contact;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import com.poc.model.dto.ResultUnique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactCustomRepository {
    public Page<Contact> findContactsByProperties(String firstName, String lastName, String email, String company, String title, Source source, Pageable pageable);
    public List<Contact> findAllContacts();
    public Page<Contact> searchContactsByName(String name, Pageable pageable);
    public List<Contact> matchContactsBySource(Source source);
    public List<Contact> projectContactsBySource(Source source);
    public List<ContactInfoAggregate> countContactsBySource();
    public List<ResultUnique> findDistinctSourceValues();
    public List<ContactInfoAggregate> groupContactsBySource();
    public List<Contact> findContactsBySourceAndLimit(Source source, long maxDocuments);
    public List<Contact> findContactsBySourceAndSkip(Source source, long elementsToSkip);
    public List<SaleInfoAggregate> getTotalSalesPerContact();
    public List<Sale> listContactSales();
    public List<SalesOwner> findSalesOwnersBySource(Source source);
    public List<SalesOwner> groupSalesOwnersBySource(Source source);
    // public List<Contact> findContactsByProperties(String firstName, String lastName, String email, String company, String title, Source source, Integer page);
    public Page<Contact> searchByName(String name, Pageable pageable);
    public List<SaleInfoAggregate> getBigSalesPerContact();
    public List<SaleInfoAggregate> findLobSalesByContact();
    public List<SaleInfoAggregate> findFirstSaleForEachContact();

}
