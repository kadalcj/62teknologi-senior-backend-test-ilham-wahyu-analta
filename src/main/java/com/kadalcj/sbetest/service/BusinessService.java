package com.kadalcj.sbetest.service;

import com.kadalcj.sbetest.domain.model.Business;
import com.kadalcj.sbetest.domain.model.Category;
import com.kadalcj.sbetest.domain.repository.BusinessRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final EntityManager entityManager;

    public BusinessService(BusinessRepository businessRepository, EntityManager entityManager) {
        this.businessRepository = businessRepository;
        this.entityManager = entityManager;
    }

    public List<Business> getAll() {
        return businessRepository.findAll();
    }

    public List<Business> getBusinessBySearch(List<String> categories, List<Integer> price, String sort_by, Integer limit, Integer offset) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> query = cb.createQuery(Business.class);
        Root<Business> root = query.from(Business.class);

        query.select(root);

        Join<Business, Category> categoryJoin = root.join("categories");
        if (categories != null && !categories.isEmpty()) {
            List<Predicate> predicatesAlias = categories.stream()
                    .map(alias -> cb.equal(categoryJoin.get("alias"), alias))
                    .toList();

            query.where(cb.or(predicatesAlias.toArray(new Predicate[0])));
        }

        if (price != null && !price.isEmpty()) {
            List<Predicate> predicatePrice = price.stream()
                    .map(priceFilter -> cb.equal(root.get("price"), convertPrice(priceFilter)))
                    .toList();

            query.where(cb.or(predicatePrice.toArray(new Predicate[0])));
        }

        if (sort_by != null) {
            switch (sort_by) {
                case "rating":
                    query.orderBy(cb.desc(root.get("rating")));
                case "review_count":
                    query.orderBy(cb.desc(root.get("reviewCount")));
                case "distance":
                    query.orderBy(cb.asc(root.get("Distance")));
                default:
                    /// Best Match
            }
        }

        TypedQuery<Business> typedQuery = entityManager.createQuery(query);
        if (limit != null) typedQuery.setMaxResults(limit);
        if (offset != null) typedQuery.setFirstResult(offset);

        return typedQuery.getResultList();
    }

    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    public List<Business> batchCreateBusiness(List<Business> businesses) {
        return businessRepository.saveAll(businesses);
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

    public String convertPrice(Integer price) {
        return switch (price) {
            case 1 -> "$";
            case 2 -> "$$";
            case 3 -> "$$$";
            case 4 -> "$$$$";
            default -> "";
        };
    }
}
