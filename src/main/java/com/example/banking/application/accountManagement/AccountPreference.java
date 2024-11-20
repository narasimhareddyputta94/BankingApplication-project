package com.example.banking.application.accountManagement;


import java.time.LocalDateTime;

import com.example.banking.application.authentication.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "Preferences")
@NoArgsConstructor
@AllArgsConstructor
public class AccountPreference {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long preferenceId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@NotNull(message = "User Id Required")
	private User user;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	@NotNull
	private PreferenceType preferenceType;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	@NotNull
	private PreferenceValue preferenceValue;
	
	public void setPreferenceTypeEnum(PreferenceType pref) {
		this.preferenceType = pref;
	}
	
	public void setPreferenceValueEnum(PreferenceValue pref) {
		this.preferenceValue = pref;
	}
	public PreferenceType getPreferenceType() {
		return preferenceType;
	}
	public PreferenceValue getPreferenceValue() {
		return preferenceValue;
	}
	
	//@NotNull
	//private LocalDateTime createdOn;
	@NotNull
    private LocalDateTime updatedOn;
    
    public enum PreferenceType {
        COMMUNICATION,
        ALERTS,
        DISPLAY
    }

    public enum PreferenceValue {
        EMAIL,
        PHONE,
        PHYSICAL_MAIL,
        SMS
    }
}