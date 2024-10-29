package com.example.Banking.application.accountManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCreationRepo extends JpaRepository<AccountCreation, Long> {

}
