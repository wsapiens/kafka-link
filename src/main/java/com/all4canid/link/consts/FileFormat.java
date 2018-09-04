package com.all4canid.link.consts;

public enum FileFormat {
	CSV("csv"),
	JSON("json"),
	ZIP("zip");

	private final String name;

	private FileFormat(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equals(otherName);
	}

	public String toString(){
		return name;
	}

}
