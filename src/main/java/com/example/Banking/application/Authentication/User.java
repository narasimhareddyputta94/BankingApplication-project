package com.example.Banking.application.Autentication;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class User {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String username;
    private String email;
    private String password;
    
    //using manually because had some issues with lombok
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

