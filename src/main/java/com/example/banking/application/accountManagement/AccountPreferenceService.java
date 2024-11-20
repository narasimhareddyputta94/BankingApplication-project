package com.example.banking.application.accountManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class AccountPreferenceService {

	@Autowired
	private AccountPreferenceRepo repo;
	
	public List<AccountPreference> list() {
        log.traceEntry("Enter list");
        var retval = repo.findAll();
        log.traceExit("Exit list", retval);        
        return retval;
    }
	
	public AccountPreference save(AccountPreference preference) {
        log.traceEntry("enter save", preference);
        repo.save(preference);
        log.traceExit("exit save", preference);        
        return preference;
}
	
	 public void delete(long id) {
	        log.traceEntry("Enter delete", id);
	        repo.deleteById(id);
	        log.traceExit("Exit delete");
	    }    
}