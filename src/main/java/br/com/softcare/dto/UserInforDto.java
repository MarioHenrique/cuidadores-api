package br.com.softcare.dto;

import br.com.softcare.entities.User;

public class UserInforDto {

	private String name;
	private String email;
	private String zipcode;
	private String contact;
	
	public UserInforDto(User user) {
		this.name=user.getName();
		this.email=user.getEmail();
		this.zipcode = user.getZipCode();
		this.contact = user.getContact();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
