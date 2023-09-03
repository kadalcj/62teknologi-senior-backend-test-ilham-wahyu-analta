package com.kadalcj.sbetest.domain.repository;

import com.kadalcj.sbetest.domain.model.Business;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
}