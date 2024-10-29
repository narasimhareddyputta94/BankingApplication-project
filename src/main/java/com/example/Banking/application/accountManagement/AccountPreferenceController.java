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
import org.springframework.validation.FieldError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/api/preferences")
@Tag(name = "AccountPreference", description = "Controller layer for the preferences table")
@Log4j2
public class AccountPreferenceController {
	
	@Autowired
	private AccountPreferenceService service;
	
	@GetMapping
	@Operation(summary = "Retrieves all tuples in preferences table in the database")
	@ApiResponse(responseCode = "200", description = "Good", content = {@Content(mediaType="application/json", schema=@Schema(implementation=AccountPreference.class))})
	public List<AccountPreference> list(){
		return service.list();
	}
	
	 @PostMapping
	    @Operation(summary = "Save the Preference to the database and return the preference Id")
	    public long createAccount( @Valid @RequestBody AccountPreference preference) {
		 System.out.println(preference);
	        log.traceEntry("enter save", preference);
	        service.save(preference);
	        log.traceExit("exit save", preference);
	        System.out.println(preference);
	        return preference.getPreferenceId();
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
	    @Operation(summary = "Delete the preference based on the id given")
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
