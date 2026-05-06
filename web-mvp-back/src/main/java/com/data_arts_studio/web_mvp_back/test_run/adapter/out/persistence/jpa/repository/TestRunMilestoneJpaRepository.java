package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestRunMilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.id.TestRunMilestoneJpaId;

@Repository
public interface TestRunMilestoneJpaRepository 
        extends JpaRepository<TestRunMilestoneJpaEntity, TestRunMilestoneJpaId> {


}
