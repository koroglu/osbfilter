package com.oracle.acs.model;

import java.io.Serializable;

public class WhiteListModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long whiteListId;
	private String context;
	private String ipAddress;

	
	public WhiteListModel(Long whiteListId, String context, String ipAddress) {
		super();
		this.whiteListId = whiteListId;
		this.context = context;
		this.ipAddress = ipAddress;
	}
	
	public WhiteListModel(String context, String ipAddress) {
		this.context = context;
		this.ipAddress = ipAddress;
	}
	
	
	
	
	
	public Long getWhiteListId() {
		return whiteListId;
	}

	public void setWhiteListId(Long whiteListId) {
		this.whiteListId = whiteListId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	

}
