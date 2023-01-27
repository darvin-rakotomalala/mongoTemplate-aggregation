package com.poc.repository;

import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Contact;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import com.poc.model.dto.ResultUnique;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
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
    public Page<Contact> searchContactsByName(String name, Pageable pageable) {

        String regexName = StringUtils.isEmpty(name) ? "" : name;
        Criteria nameCriteria = new Criteria();
        nameCriteria.orOperator(Criteria.where("lastName").regex(regexName, "i"), Criteria.where("firstName").regex(regexName, "i"));

        MatchOperation nameMatch = Aggregation.match(nameCriteria);
        AggregationOperation sortByName = Aggregation.sort(Sort.Direction.ASC, "lastName");

        AggregationOperation skipToPageNumber = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        AggregationOperation limitToPageSize = Aggregation.limit(pageable.getPageSize());

        Aggregation agg = Aggregation.newAggregation(nameMatch, sortByName, skipToPageNumber, limitToPageSize);
        AggregationResults<Contact> results = mongoTemplate.aggregate(agg, CONTACT_COLLECTION, Contact.class);

        List<Contact> contactList = results.getMappedResults();

        Query query = new Query().with(pageable);
        return PageableExecutionUtils.getPage(contactList, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1)
                        .skip(-1), Contact.class));
    }

    @Override
    public List<Contact> matchContactsBySource(Source source) {
        AggregationOperation matchSource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation sortByLastName = Aggregation.sort(Sort.Direction.ASC, "lastName");

        Aggregation agg = Aggregation.newAggregation(matchSource, sortByLastName);

        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, Contact.class).getMappedResults();
    }

    @Override
    public List<Contact> projectContactsBySource(Source source) {
        AggregationOperation matchSource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation sortByLastName = Aggregation.sort(Sort.Direction.ASC, "lastName");
        AggregationOperation projectSource = Aggregation.project("firstName", "lastName", "source").andExclude("_id");

        Aggregation agg = Aggregation.newAggregation(matchSource, sortByLastName, projectSource);

        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, Contact.class).getMappedResults();
    }

    @Override
    public List<ContactInfoAggregate> countContactsBySource() {
        AggregationOperation groupCount = Aggregation.group("source").count().as("count");
        AggregationOperation projectCount = Aggregation.project("count").and("source").previousOperation();

        Aggregation agg = Aggregation.newAggregation(groupCount, projectCount);

        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, ContactInfoAggregate.class).getMappedResults();
    }

    @Override
    public List<ResultUnique> findDistinctSourceValues() {
        AggregationOperation groupSource = Aggregation.group("source");
        Aggregation agg = Aggregation.newAggregation(groupSource);
        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, ResultUnique.class).getMappedResults();
    }

    @Override
    public List<ContactInfoAggregate> groupContactsBySource() {
        AggregationOperation sortByLastName = Aggregation.sort(Sort.Direction.ASC, "lastName");
        AggregationOperation fullName = Aggregation.project("source").and("firstName").concat(" ", Aggregation.fields("lastName")).as("fullName");
        AggregationOperation groupSource = Aggregation.group("source").push("fullName").as("contacts");
        AggregationOperation projectSource = Aggregation.project("contacts").and("source").previousOperation();

        Aggregation aggregation = Aggregation.newAggregation(sortByLastName, fullName, groupSource, projectSource);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, ContactInfoAggregate.class).getMappedResults();
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
