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
public class WhiteListDAO extends BaseDAO {

	@Autowired
	CacheService cacheService;
	

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@PostConstruct
	public void init() {
		
		List<Map<String, Object>> queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST");
		cacheService.getCacheWhiteList().put("all",queryForList);
		
		Map<String,String> ipAndAddressList = cacheService.getIpAndContextList();
		
		for (Map<String, Object> map : queryForList) {
			System.out.println(map.get("context"));
			ipAndAddressList.put(map.get("context")+"_"+map.get("ip_address"), map.get("white_list_id").toString());
		}
		
	}

	public List<Map<String, Object>> listIpByContext(String context) {
		List<Map<String, Object>> queryForList = null;
		if (cacheService.getGetIpByContext().get(context) == null ) {
			queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST WHERE context =  ?", context);
			if (queryForList.size()>0 ) {
				cacheService.getGetIpByContext().put(context, queryForList);	
			}
		} else {
			queryForList = cacheService.getGetIpByContext().get(context);
		}
		
		return queryForList;
	}

	public boolean doIHavePermission(String context, String ipAddress, int listenMode) {
		Integer kacTaneIpAdresiVar = 0;
		if (listenMode == 1) {
			saveWhiteListModel(new WhiteListModel(context, ipAddress));
			return true;
		}
		
		if (cacheService.getIpAndContextList().containsKey(context+"_"+ipAddress)) return true;
		
		kacTaneIpAdresiVar = jdbcTemplate().queryForObject("SELECT count(1) from WHITE_LIST WHERE context =  ? and ip_address like ?", Integer.class, context,ipAddress);
		
		return kacTaneIpAdresiVar > 0 ? true : false;
	}

	public boolean saveWhiteListModel(WhiteListModel model) {
		try {
			int update = jdbcTemplate().update(
					"INSERT INTO WHITE_LIST (white_list_id, context, ip_address )  VALUES ( SEQ_WHITE_LIST.nextval, ? , ?  ) ",
					model.getContext(), model.getIpAddress());
			
			List queryForList = jdbcTemplate().queryForList("SELECT * from WHITE_LIST WHERE context =  ?", model.getContext());
			cacheService.getGetIpByContext().put(model.getContext(), queryForList);
			
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
