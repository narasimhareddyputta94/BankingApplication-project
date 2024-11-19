package com.example.Banking.application.accountManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/accounts")
@Tag(name = "AccountCreation", description = "Controller layer for the accounts table")
@Log4j2
public class AccountCreationController {
	
	@Autowired
	private AccountCreationService service;
	
	@GetMapping
	@Operation(summary = "Retrieves all accounts in the database")
	@ApiResponse(responseCode = "200", description = "Good", content = {@Content(mediaType="application/json", schema=@Schema(implementation=AccountCreation.class))})
	public List<AccountCreation> list(){
		return service.list();
	}
	
	 @PostMapping
	    @Operation(summary = "Save the Account to the database and return the accountId")
	    public ResponseEntity<Long> createAccount( @Valid @RequestBody AccountCreation account) {
		 System.out.println(account);
	        log.traceEntry("enter save", account);
	        service.save(account);
	        log.traceExit("exit save", account);
	        System.out.println(account);
	        //return account.getAccountId();
	        return new ResponseEntity<>(account.getAccountId(), HttpStatus.CREATED);
	        
	    }
	 
//	 @PostMapping("/validated")
//	    @Operation(summary = "Save the Account and returns the Account ID")
//	    public ResponseEntity<String> validatedSave(@Valid @RequestBody AccountCreation account) {
//	        log.traceEntry("enter save", account);
//	        service.save(account);
//	        log.traceExit("exit save", account);
//	        return ResponseEntity.ok("new id is " + account.getAccountId());
//	    }
	 
	 @DeleteMapping
	    @Operation(summary = "Delete the account based on the id given")
	    public void delete(long id) {
	        log.traceEntry("Enter delete", id);
	        service.delete(id);
	        log.traceExit("Exit delete");
	    }
	 
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return errors;
	    }
	 
	 
}
