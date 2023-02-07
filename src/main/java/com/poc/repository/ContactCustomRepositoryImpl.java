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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.aggregation.*;
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
        AggregationOperation matchBySource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation sortByLastName = Aggregation.sort(Sort.Direction.ASC, "lastName");
        AggregationOperation limit = Aggregation.limit(maxDocuments);

        Aggregation aggregation = Aggregation.newAggregation(matchBySource, sortByLastName, limit);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, Contact.class).getMappedResults();
    }

    @Override
    public List<Contact> findContactsBySourceAndSkip(Source source, long elementsToSkip) {
        AggregationOperation matchBySource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation sortByLastName = Aggregation.sort(Sort.Direction.ASC, "lastName");
        AggregationOperation skip = Aggregation.skip(elementsToSkip);

        Aggregation aggregation = Aggregation.newAggregation(matchBySource, sortByLastName, skip);

        return mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Contact.class), Contact.class).getMappedResults();
    }

    @Override
    public List<SaleInfoAggregate> getTotalSalesPerContact() {
        AggregationOperation matchBySales = Aggregation.match(Criteria.where("sales").exists(true));
        AggregationOperation unwindBySales = Aggregation.unwind("sales");
        AggregationOperation fullName = Aggregation.project("_id", "sales").and("firstName").concat(" ", Aggregation.fields("lastName")).as("contactName");
        AggregationOperation groupByContactName = Aggregation.group("contactName").sum("sales.value").as("totalSales");
        AggregationOperation project = Aggregation.project("totalSales").and("contactName").previousOperation();

        Aggregation aggregation = Aggregation.newAggregation(matchBySales, unwindBySales, fullName, groupByContactName, project);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, SaleInfoAggregate.class).getMappedResults();
    }

    @Override
    public List<Sale> listContactSales() {
        AggregationOperation matchBySales = Aggregation.match(Criteria.where("sales").exists(true));
        AggregationOperation unwindBySales = Aggregation.unwind("sales");
        AggregationOperation sortBySalesDate = Aggregation.sort(Sort.Direction.ASC, "sales.date");
        AggregationOperation replaceRootSales = Aggregation.replaceRoot("sales");

        Aggregation aggregation = Aggregation.newAggregation(matchBySales, unwindBySales, sortBySalesDate, replaceRootSales);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, Sale.class).getMappedResults();
    }

    @Override
    public List<SalesOwner> findSalesOwnersBySource(Source source) {
        AggregationOperation matchBySource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation replaceRootSalesOwner = Aggregation.replaceRoot("salesOwner");

        Aggregation aggregation = Aggregation.newAggregation(matchBySource, replaceRootSalesOwner);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, SalesOwner.class).getMappedResults();
    }

    @Override
    public List<SalesOwner> groupSalesOwnersBySource(Source source) {
        AggregationOperation matchBySource = Aggregation.match(Criteria.where("source").is(source));
        AggregationOperation replaceRootSalesOwner = Aggregation.replaceRoot("salesOwner");
        AggregationOperation groupByName = Aggregation.group("lastName", "firstName");

        Aggregation aggregation = Aggregation.newAggregation(matchBySource, replaceRootSalesOwner, groupByName);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, SalesOwner.class).getMappedResults();
    }

    @Override
    public Page<Contact> searchByName(String name, Pageable pageable) {
        Criteria nameCriteria = new Criteria();
        if (!StringUtils.isBlank(name)) {
            nameCriteria = nameCriteria.orOperator(Criteria.where("firstName").is(name), Criteria.where("lastName").is(name));
        }

        Query query = new Query();
        query.addCriteria(nameCriteria);
        query.with(pageable);
        query.skip((long) pageable.getPageSize() * pageable.getPageNumber());
        query.limit(pageable.getPageSize());

        List<Contact> contacts = mongoTemplate.find(query, Contact.class);
        long count = mongoTemplate.count(query.skip(-1).limit(-1), Contact.class);

        return new PageImpl<>(contacts, pageable, count);
    }

    @Override
    public List<SaleInfoAggregate> getBigSalesPerContact() {
        ConditionalOperators.Cond bigSalesCount = ConditionalOperators
                .when(new Criteria("sales.value")
                        .gte(200000))
                .then(1)
                .otherwise(0);

        AggregationOperation unwindSales = Aggregation.unwind("sales", true);
        AggregationOperation fullName = Aggregation.project("_id", "sales").and("firstName").concat(" ", Aggregation.fields("lastName")).as("contactName");
        AggregationOperation groupByName = Aggregation.group("contactName").sum(bigSalesCount).as("bigSales");
        AggregationOperation project = Aggregation.project("bigSales").and("contactName").previousOperation();

        Aggregation agg = Aggregation.newAggregation(unwindSales, fullName, groupByName, project);

        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, SaleInfoAggregate.class).getMappedResults();
    }

    @Override
    public List<SaleInfoAggregate> findLobSalesByContact() {
        AggregationOperation unwindSales = Aggregation.unwind("sales", true);
        AggregationOperation fullName = Aggregation.project("_id", "sales").and("firstName").concat(" ", Aggregation.fields("lastName")).as("contactName");
        AggregationOperation groupName = Aggregation.group("contactName").addToSet("sales.lineOfBusiness").as("lobs");
        AggregationOperation project = Aggregation.project("lobs").and("contactName").previousOperation();

        Aggregation agg = Aggregation.newAggregation(unwindSales, fullName, groupName, project);

        return mongoTemplate.aggregate(agg, CONTACT_COLLECTION, SaleInfoAggregate.class).getMappedResults();
    }

    @Override
    public List<SaleInfoAggregate> findFirstSaleForEachContact() {
        AggregationOperation matchBySales = Aggregation.match(Criteria.where("sales").exists(true));
        AggregationOperation unwindSales = Aggregation.unwind("sales");
        AggregationOperation sortBySalesDate = Aggregation.sort(Sort.Direction.ASC, "sales.date");
        AggregationOperation fullName = Aggregation.project("_id", "sales").and("firstName").concat(" ", Aggregation.fields("lastName")).as("contactName");
        AggregationOperation groupByName = Aggregation.group("contactName").first("sales").as("sale");
        AggregationOperation project = Aggregation.project("sale").and("contactName").previousOperation();

        Aggregation aggregation = Aggregation.newAggregation(matchBySales, unwindSales, sortBySalesDate, fullName, groupByName, project);

        return mongoTemplate.aggregate(aggregation, CONTACT_COLLECTION, SaleInfoAggregate.class).getMappedResults();
    }

}
