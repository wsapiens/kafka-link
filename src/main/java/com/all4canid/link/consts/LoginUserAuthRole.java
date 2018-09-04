package com.all4canid.link.consts;

public enum LoginUserAuthRole {

	USER("ROLE_USER"),
	AMDIN("ROLE_ADMIN");

	private final String name;

	private LoginUserAuthRole(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equalsIgnoreCase(otherName);
	}

	public String toString(){
		return name;
	}
}
