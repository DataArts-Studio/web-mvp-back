package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunMilestoneJpaRepository 
        extends JpaRepository<TestRunMilestoneJpaEntity, TestRunMilestoneJpaId> {


}
