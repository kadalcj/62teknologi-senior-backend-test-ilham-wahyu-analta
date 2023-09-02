package com.kadalcj.sbetest.service;

import com.kadalcj.sbetest.domain.model.Business;
import com.kadalcj.sbetest.domain.repository.BusinessRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<Business> getAll() {
        return businessRepository.findAll();
    }

    public List<Business> getBusinessByCriteria(int limit, int offset) {
        return null;
    }

    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    public Business updateBusiness(Long id, Business updateBusiness) {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if (optionalBusiness.isEmpty()) throw new EntityNotFoundException("Something");

        Business existingBusiness = optionalBusiness.get();
        if (existingBusiness.equals(updateBusiness)) return null;

        existingBusiness.setAlias(updateBusiness.getAlias());
        existingBusiness.setName(updateBusiness.getName());
        existingBusiness.setImageUrl(updateBusiness.getImageUrl());
        existingBusiness.setClosed(updateBusiness.isClosed());
        existingBusiness.setReviewCount(updateBusiness.getReviewCount());
        existingBusiness.setCategories(updateBusiness.getCategories());
        existingBusiness.setRating(updateBusiness.getRating());
        existingBusiness.setCoordinates(updateBusiness.getCoordinates());
        existingBusiness.setTransactions(updateBusiness.getTransactions());
        existingBusiness.setPrice(updateBusiness.getPrice());
        existingBusiness.setLocation(updateBusiness.getLocation());

        return businessRepository.save(existingBusiness);
    }

    public boolean removeBusiness(Long id) {
        if (!businessRepository.existsById(id)) return false;

        businessRepository.deleteById(id);
        return true;
    }
}
