package com.poc.controller;

import com.poc.constraint.validation.ContactValidator;
import com.poc.model.aggregate.ContactInfoAggregate;
import com.poc.model.aggregate.SaleInfoAggregate;
import com.poc.model.constant.Source;
import com.poc.model.domain.Sale;
import com.poc.model.domain.SalesOwner;
import com.poc.model.dto.ContactDTO;
import com.poc.service.application.ContactCUDSA;
import com.poc.service.application.ContactRSA;
import com.poc.utils.HelpPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "contacts")
public class ContactController {

    private final ContactCUDSA contactCUDSA;
    private final ContactRSA contactRSA;
    private ContactValidator contactValidator;

    @InitBinder("contactDTO")
    protected void initContactDTOBinder(WebDataBinder binder) {
        binder.setValidator(contactValidator);
    }

    @Operation(summary = "WS used to save all contacts")
    @PostMapping("/saveAll")
    public List<ContactDTO> saveAllContacts(@RequestBody List<ContactDTO> contacts) {
        return contactCUDSA.saveAllContacts(contacts);
    }

    @Operation(summary = "WS used to get all contacts")
    @GetMapping("/all")
    public List<ContactDTO> getAllContacts() {
        return contactRSA.getAllContacts();
    }

    @Operation(summary = "WS used to get match contacts by source")
    @GetMapping("/matchBySource")
    public List<ContactDTO> matchContactsBySource(
            @RequestParam(name = "source", required = true) Source source) {
        return contactRSA.matchContactsBySource(source);  // -> Source.EMAIL
    }

    @Operation(summary = "WS used to get project contacts by source")
    @GetMapping("/projectBySource")
    public List<ContactDTO> projectContactsBySource(
            @RequestParam(name = "source", required = true) Source source) {
        return contactRSA.projectContactsBySource(source);  // -> Source.EMAIL
    }

    @Operation(summary = "WS used to get count contacts by source")
    @GetMapping("/countBySource")
    public List<ContactInfoAggregate> countContactsBySource() {
        return contactRSA.countContactsBySource();
    }

    @Operation(summary = "WS used to get distinct contacts by source")
    @GetMapping("/distinctBySource")
    public List<ContactDTO> getDistinctSourceValues() {
        return contactRSA.getDistinctSourceValues();
    }

    @Operation(summary = "WS used to get group contacts by source")
    @GetMapping("/groupBySource")
    public List<ContactInfoAggregate> groupContactsBySource() {
        return contactRSA.groupContactsBySource();
    }

    @Operation(summary = "WS used to get and limit contacts by source")
    @GetMapping("/limitBySource")
    public List<ContactDTO> getContactsBySourceAndLimit(
            @RequestParam(name = "source", required = true) Source source,
            @RequestParam(name = "maxDocuments", required = true) long maxDocuments) {
        return contactRSA.getContactsBySourceAndLimit(source, maxDocuments);
    }

    @Operation(summary = "WS used to get and limit contacts by source")
    @GetMapping("/skipBySource")
    public List<ContactDTO> getContactsBySourceAndSkip(
            @RequestParam(name = "source", required = true) Source source,
            @RequestParam(name = "elementsToSkip", required = true) long elementsToSkip) {
        return contactRSA.getContactsBySourceAndSkip(source, elementsToSkip);
    }

    @Operation(summary = "WS used to get total sales per contact")
    @GetMapping("/totalSales")
    public List<SaleInfoAggregate> getTotalSalesPerContact() {
        return contactRSA.getTotalSalesPerContact();
    }

    @Operation(summary = "WS used to get list contact sales")
    @GetMapping("/sales")
    public List<Sale> getListContactsSales() {
        return contactRSA.listContactSales();
    }

    @Operation(summary = "WS used to get sales owners by source")
    @GetMapping("/salesOwnersBySource")
    public List<SalesOwner> getSalesOwnersBySource(
            @RequestParam(name = "source", required = true) Source source) {
        return contactRSA.getSalesOwnersBySource(source); // -> Source.EMAIL
    }

    @Operation(summary = "WS used to get group sales owners by source")
    @GetMapping("/groupSalesOwnersBySource")
    public List<SalesOwner> groupSalesOwnersBySource(
            @RequestParam(name = "source", required = true) Source source) {
        return contactRSA.groupSalesOwnersBySource(source); // -> Source.EMAIL
    }

    @Operation(summary = "WS used to search contacts by name")
    @GetMapping("/byName")
    public HelpPage<ContactDTO> searchByName(
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "15", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
        return contactRSA.searchByName(name, pageable);
    }

    @Operation(summary = "WS used to search contacts by properties")
    @GetMapping("/byProperties")
    public HelpPage<ContactDTO> getContactsByProperties(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Source source,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "15", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRSA.getContactsByProperties(firstName, lastName, email, company, title, source, pageable);
    }

    @Operation(summary = "WS used to get big sales per contact")
    @GetMapping("/bigSales")
    public List<SaleInfoAggregate> getBigSalesPerContact() {
        return contactRSA.getBigSalesPerContact();
    }

    @Operation(summary = "WS used to find lob sales by contact")
    @GetMapping("/lobSales")
    public List<SaleInfoAggregate> getLobSalesByContact() {
        return contactRSA.getLobSalesByContact();
    }

    @Operation(summary = "WS used to find first sale forEach contact")
    @GetMapping("/firstSale")
    public List<SaleInfoAggregate> getFirstSaleForEachContact() {
        return contactRSA.getFirstSaleForEachContact();
    }

}
