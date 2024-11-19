package com.example.Banking.application.accountManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class AccountCreationService {

    @Autowired
    private AccountCreationRepo repo;

    public List<AccountCreation> list() {
        log.traceEntry("Enter list");
        var retval = repo.findAll();
        log.traceExit("Exit list", retval);
        return retval;
    }

    public AccountCreation save(AccountCreation account) {
        log.traceEntry("enter save", account);
        repo.save(account);
        log.traceExit("exit save", account);
        return account;
    }

    public void delete(long id) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }
}