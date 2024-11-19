package com.example.banking.application.accountManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCreationRepo extends JpaRepository<AccountCreation, Long> {

}