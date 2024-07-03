package com.oracle.acs.cache;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import jakarta.annotation.PostConstruct;

@Component
public class CacheService {

	private NamedCache<String,List<String>> cacheWhileList ;
	
	@PostConstruct
	public void init() {
		System.out.println("Initializing Cache Service...");
//		cacheWhileList= CacheFactory.getCache("cacheWhileList");
	}

	
	public void putCache(List<String> listIpAddress, String context) {
		cacheWhileList.put(context, listIpAddress);
		
	}
	
	public List<String> getIpAddressFromCache(String context) {
		return cacheWhileList.get(context);
	}
	
}
