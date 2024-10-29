package com.example.Banking.application.accountManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountPreferenceRepo extends JpaRepository<AccountPreference, Long> {

}
