package com.kadalcj.sbetest.controller;

import com.kadalcj.sbetest.domain.model.Business;
import com.kadalcj.sbetest.service.BusinessService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/business")
public class BusinessController {
    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity<HashMap<String, List<Business>>> getAll() {
        List<Business> businessList = businessService.getAll();

        if (businessList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        HashMap<String, List<Business>> result = new HashMap<>();
        result.put("businesses", businessList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HashMap<String, List<Business>>> getSearch(
            @RequestParam(name = "limit", required = false) int limit,
            @RequestParam(name = "offset", required = false) int offset
    ) {
        List<Business> businessList = businessService.getBusinessByCriteria(limit, offset);

        HashMap<String, List<Business>> result = new HashMap<>();
        result.put("businesses", businessList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Business> createBusiness(@Valid @RequestBody Business business) {
        return new ResponseEntity<>(businessService.createBusiness(business), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @Valid @RequestBody Business updatedBusiness) {
        Business updated = businessService.updateBusiness(id, updatedBusiness);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeBusiness(@PathVariable Long id) {
        if (!businessService.removeBusiness(id)) throw new EntityNotFoundException("Business with ID " + id + " not found!");

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Business with ID " + id + " successfully removed!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}