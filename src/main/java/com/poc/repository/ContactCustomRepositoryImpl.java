package com.poc.repository;

import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Contact;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactCustomRepositoryImpl implements ContactCustomRepository {

    public static final String CONTACT_COLLECTION = "contacts";
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Contact> findContactsByProperties(String firstName, String lastName, String email, String company, String title, Source source, Pageable pageable) {

        final Query query = new Query().with(pageable);
        // query.fields().include("id").include("name");
        final List<Criteria> criteria = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty())
            criteria.add(Criteria.where("firstName").is(firstName));
        if (lastName != null && !lastName.isEmpty())
            criteria.add(Criteria.where("lastName").is(lastName));
        if (email != null && !email.isEmpty())
            criteria.add(Criteria.where("email").is(email));
        if (company != null && !company.isEmpty())
            criteria.add(Criteria.where("company").in(company));
        if (title != null && !title.isEmpty())
            criteria.add(Criteria.where("title").is(title));
        if (source != null)
            criteria.add(Criteria.where("source").is(source));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Contact.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Contact.class)
        );
    }

    @Override
    public List<Contact> findAllContacts() {
        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "lastName");
        Aggregation agg = Aggregation.newAggregation(sort);
        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, Contact.class).getMappedResults();
    }

    @Override
    public List<Contact> matchContactsBySource(Source source) {
        return null;
    }

    @Override
    public List<Contact> projectContactsBySource(Source source) {
        return null;
    }

    @Override
    public List<ContactInfoAggregate> countContactsBySource() {
        return null;
    }

    @Override
    public List<Contact> findDistinctSourceValues() {
        return null;
    }

    @Override
    public List<ContactInfoAggregate> groupContactsBySource() {
        return null;
    }

    @Override
    public List<Contact> findContactsBySourceAndLimit(Source source, long maxDocuments) {
        return null;
    }

    @Override
    public List<Contact> findContactsBySourceAndSkip(Source source, long elementsToSkip) {
        return null;
    }

    @Override
    public List<SaleInfoAggregate> getTotalSalesPerContact() {
        return null;
    }

    @Override
    public List<Sale> listContactSales() {
        return null;
    }

    @Override
    public List<SalesOwner> findSalesOwnersBySource(Source source) {
        return null;
    }

    @Override
    public List<SalesOwner> groupSalesOwnersBySource(Source source) {
        return null;
    }

    @Override
    public Page<Contact> searchByName(String name, Pageable pageable) {
        return null;
    }

    @Override
    public List<SaleInfoAggregate> getBigSalesPerContact() {
        return null;
    }

    @Override
    public List<SaleInfoAggregate> findLobSalesByContact() {
        return null;
    }

    @Override
    public List<SaleInfoAggregate> findFirstSaleForEachContact() {
        return null;
    }

}
