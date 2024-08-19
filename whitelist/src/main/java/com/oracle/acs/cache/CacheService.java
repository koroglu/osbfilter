package com.oracle.acs.cache;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import jakarta.annotation.PostConstruct;

@Component
public class CacheService {

	private NamedCache<String,List<Map<String,Object>>> cacheWhiteList ;
	
	private NamedCache<String, List<Map<String, Object>>> getIpByContext;
	
	private NamedCache<String, String> ipAndContextList;
	
	@PostConstruct
	public void init() {
		System.out.println("Initializing Cache Service...");
		setCacheWhiteList(CacheFactory.getCache("cacheWhileList"));
		getIpByContext  = CacheFactory.getCache("getIpByContext");
		setIpAndContextList(CacheFactory.getCache("ipAndContextList"));
		
	}

	public NamedCache<String, List<Map<String, Object>>> getGetIpByContext() {
		return getIpByContext;
	}



	public void setGetIpByContext(NamedCache<String, List<Map<String, Object>>> getIpByContext) {
		this.getIpByContext = getIpByContext;
	}


	public NamedCache<String,List<Map<String,Object>>> getCacheWhiteList() {
		return cacheWhiteList;
	}



	public void setCacheWhiteList(NamedCache<String,List<Map<String,Object>>> cacheWhiteList) {
		this.cacheWhiteList = cacheWhiteList;
	}

	public NamedCache<String, String> getIpAndContextList() {
		return ipAndContextList;
	}

	public void setIpAndContextList(NamedCache<String, String> ipAndContextList) {
		this.ipAndContextList = ipAndContextList;
	}

	
	
}
