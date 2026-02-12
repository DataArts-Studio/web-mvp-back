package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseJpaRepository extends JpaRepository<TestCaseJpaEntity, String> {
    
}
