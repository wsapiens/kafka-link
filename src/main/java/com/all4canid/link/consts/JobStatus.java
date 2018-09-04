package com.all4canid.link.consts;

public enum JobStatus {

	STARTED("started"),
	SUCCESS("success"),
	FAIL("fail");
	
	private final String name;

	private JobStatus(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equalsIgnoreCase(otherName);
	}

	public String toString(){
		return name;
	}
}
