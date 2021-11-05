package com.copper.coppertest.persistence.repository;

import com.copper.coppertest.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The JPA repository that is used to perform operations against the DB
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String>
{
}
