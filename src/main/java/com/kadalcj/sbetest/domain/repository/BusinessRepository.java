package com.kadalcj.sbetest.domain.repository;

import com.kadalcj.sbetest.domain.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}