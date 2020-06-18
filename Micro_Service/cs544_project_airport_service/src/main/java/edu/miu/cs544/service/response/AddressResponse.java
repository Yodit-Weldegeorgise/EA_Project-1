package edu.miu.cs544.service.response;

import edu.miu.cs544.domain.Address;

public class AddressResponse {
	private String street;
	private String city;
	private String state;
	private String zip;
		
	public AddressResponse() {
		super();
	}
	
	public AddressResponse(Address address) {
		super();
		if(address != null) {
			this.street = address.getStreet();
			this.city = address.getCity();
			this.state = address.getState();
			this.zip = address.getZip();
		}
	}
	
	public AddressResponse(String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public AddressResponse(Integer id, String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
