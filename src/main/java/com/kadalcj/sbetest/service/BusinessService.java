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
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Business> query = builder.createQuery(Business.class);
        Root<Business> root = query.from(Business.class);
        Join<Business, Category> categoryJoin = root.join("categories");

        // Select All
        query.select(root);

        /// Filter Category
        List<Predicate> categoryPredicates = new ArrayList<>();
        if (categories != null) {
            for (String categoryAlias : categories) {
                Predicate aliasPredicate = builder.equal(categoryJoin.get("alias"), categoryAlias);
                categoryPredicates.add(aliasPredicate);
            }
        }

        /// Filter Price
        List<Predicate> pricePredicates = new ArrayList<>();
        if (price != null) {
            for (Integer priceFilter : price) {
                Predicate pricePredicate = builder.equal(root.get("price"), convertPrice(priceFilter));
                pricePredicates.add(pricePredicate);
            }
        }

        // Combine Predicate
        if (!categoryPredicates.isEmpty()){
            if (!pricePredicates.isEmpty()) {
                Predicate finalPredicate = builder.and(
                        builder.or(categoryPredicates.toArray(new Predicate[0])),
                        builder.or(pricePredicates.toArray(new Predicate[0]))
                );
                query.where(finalPredicate);
            } else {
                query.where(builder.or(categoryPredicates.toArray(new Predicate[0])));
            }
        } else {
            query.where(builder.or(pricePredicates.toArray(new Predicate[0])));
        }

        /// Sort By
        Order sortOrder = null;
        if (sort_by != null) {
            if (sort_by.equals("rating")) sortOrder = builder.desc(root.get("rating"));
            if (sort_by.equals("review_count")) sortOrder = builder.desc(root.get("reviewCount"));
            if (sort_by.equals("distance")) sortOrder = builder.asc(root.get("distance"));
        }
        if (sortOrder != null) query.orderBy(sortOrder);

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
            default -> throw new EntityNotFoundException("Min Price 1, max Price 4");
        };
    }
}
