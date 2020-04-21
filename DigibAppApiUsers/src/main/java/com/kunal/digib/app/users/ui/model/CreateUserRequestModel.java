package com.kunal.digib.app.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message="First name cannot be empty")
	@Size(min=2, message= "First name must not be less than two characters")
	private String firstName;
	
	@NotNull(message="Last name cannot be empty")
	@Size(min=2, message= "Last name must not be less than two characters")
	private String lastName;
	
	@NotNull(message="Password cannot be empty")
	@Size(min=8, max=16, message="Password must be equal or grater than 8 characters and less than 16 characters")
	private String password;
	
	@NotNull(message="Email cannot be null")
	@Email
	private String email;
	
	@NotNull(message="Username cannot be empty")
	@Size(min=2, message= "Username must not be less than two characters")
	private String userName;

	@NotNull(message="Phone number cannot be empty")
	private String phoneNumber;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
