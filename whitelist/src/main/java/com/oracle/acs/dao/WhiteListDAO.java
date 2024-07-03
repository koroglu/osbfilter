package com.oracle.acs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.acs.cache.CacheService;
import com.oracle.acs.model.WhiteListModel;

import jakarta.annotation.PostConstruct;

@Component
public class WhiteListDAO extends BaseDAO{
	
	@Autowired CacheService cacheService;
	
    @Bean
    public JdbcTemplate jdbcTemplate () {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	return jdbcTemplate;
    }
    
    
	@PostConstruct
	public void init() {
		
		List<Map<String, Object>> queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST");
		for (Map<String, Object> map : queryForList) {
			System.out.println(map.get("context"));
		}
	}


	public List<Map<String, Object>> listIpByContext(String context) {
		List<Map<String, Object>> queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST WHERE context =  ?",context);
		return queryForList;
	}


	public boolean doIHavePermission(String context, String ipAddress) {
		Integer kacTaneIpAdresiVar = jdbcTemplate().queryForObject("SELECT count(1) from WHITE_LIST WHERE context =  ? and ip_address like ?",Integer.class,context,ipAddress);
		
		return kacTaneIpAdresiVar>0?true:false;
	}


	public boolean saveWhiteListModel(WhiteListModel model) {
		try {
			int update = jdbcTemplate().update("INSERT INTO WHITE_LIST (white_list_id, context, ip_address )  VALUES ( SEQ_WHITE_LIST.nextval, ? , ?  ) ",model.getContext(),model.getIpAddress());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public List<Map<String, Object>> listAll() {
		List<Map<String, Object>> queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST");
		return queryForList;
	}

	
	
}
