package com.userservice.utils;

public enum Constants {

	USER_SAVED("User Saved Successfully"), USER_DELETED("User Deleted Successfully"),
	USER_UPDATED("User Updated Successfully");

	private String value;

	private Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
